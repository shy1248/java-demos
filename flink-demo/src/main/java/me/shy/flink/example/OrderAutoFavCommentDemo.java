/**
 * @Date        : 2021-04-04 00:21:58
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 订单自动好评，如某个订单完成后10秒内（实际中更长时间，后台业务系统来做）未作出评价，则系统自动作出好评
 */
package me.shy.flink.example;

import java.time.Duration;
import java.util.Iterator;
import java.util.Properties;
import java.util.Map.Entry;

import com.google.gson.Gson;

import org.apache.flink.api.common.RuntimeExecutionMode;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.common.state.MapState;
import org.apache.flink.api.common.state.MapStateDescriptor;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.runtime.state.filesystem.FsStateBackend;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.CheckpointConfig.ExternalizedCheckpointCleanup;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.util.Collector;

import me.shy.flink.example.pojo.Order;
import me.shy.flink.example.util.Util;

public class OrderAutoFavCommentDemo {
    public static void main(String[] args) throws Exception {
        // 1:Settings
        // setting the hadoop user with remote
        // System.setProperty("HADOOP_USER_NAME", "root");
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // set runtime mode to automatic
        env.setRuntimeMode(RuntimeExecutionMode.AUTOMATIC);
        // enabled checkpoint and checkpoingting every 1 sec
        env.enableCheckpointing(10000);
        // set checkpoint state backend to hdfs
        // env.setStateBackend(new FsStateBackend("hdfs://minikube:9000/flink/checkpoint"));
        env.setStateBackend(new FsStateBackend("file:///Users/shy/Downloads/flink/checkpoint"));
        CheckpointConfig checkpointConfig = env.getCheckpointConfig();
        // checkpoint mode, default
        checkpointConfig.setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE);
        // set checkpoint concurrency
        checkpointConfig.setMaxConcurrentCheckpoints(1);
        // checkpointing timeout
        checkpointConfig.setCheckpointTimeout(1500);
        checkpointConfig.setMinPauseBetweenCheckpoints(500);
        checkpointConfig.enableUnalignedCheckpoints();
        checkpointConfig.enableExternalizedCheckpoints(ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION);
        env.setRestartStrategy(RestartStrategies.failureRateRestart(3, org.apache.flink.api.common.time.Time.minutes(5),
                org.apache.flink.api.common.time.Time.seconds(30)));

        // 2: Event Source
        Properties properties = new Properties();
        properties.load(Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("example-kafka-consumer.properties"));
        DataStreamSource<String> source = env.addSource(
                new FlinkKafkaConsumer<String>(properties.getProperty("topic"), new SimpleStringSchema(), properties),
                "Kafka source");

        // mapping json to pojo
        SingleOutputStreamOperator<Order> orderStream = source.map(msg -> new Gson().fromJson(msg, Order.class))
                .returns(Order.class);
        // 注册 watermark
        SingleOutputStreamOperator<Order> watermarkedStream = orderStream
                .assignTimestampsAndWatermarks(WatermarkStrategy.<Order>forBoundedOutOfOrderness(Duration.ofSeconds(5))
                        .withTimestampAssigner((o, t) -> o.getEventTime()));

        // 3: Transmissions
        SingleOutputStreamOperator<String> resultStream = watermarkedStream.keyBy(Order::getUserId)
                .process(new KeyedProcessFunction<Integer, Order, String>() {
                    private static final long serialVersionUID = 1L;
                    // 订单评价超时时间
                    private Long timeout = 10000L;
                    private MapState<String, Long> mapState = null;

                    @Override
                    public void open(Configuration parameters) throws Exception {
                        MapStateDescriptor<String, Long> descriptor = new MapStateDescriptor<>("OrderMapState",
                                Types.STRING, Types.LONG);
                        mapState = getRuntimeContext().getMapState(descriptor);
                    }

                    @Override
                    public void processElement(Order value, KeyedProcessFunction<Integer, Order, String>.Context ctx,
                            Collector<String> out) throws Exception {
                        // 首先将输入的订单信息存储至状态中
                        // !!!: 不能使用订单的事件事件作为 key，虽然这么做可以在 timer 触发时根据事件时间直接从状态中获取到对应的超时的订单
                        // 但是在高并发的系统中，同一个事件时间可能有多条订单产生，这样就会导致某些订单被漏掉
                        mapState.put(value.getOrderId(), value.getEventTime());
                        // 注册基于事件时间的定时器
                        ctx.timerService().registerEventTimeTimer(value.getEventTime() + timeout);
                    }

                    // 定时器到达执行时间时执行
                    @Override
                    public void onTimer(long timestamp, KeyedProcessFunction<Integer, Order, String>.OnTimerContext ctx,
                            Collector<String> out) throws Exception {
                        // 遍历状态中的订单，判断用户是否已经作出评价，如果没有评价，则系统自动好评，最后从状态中移除该订单以防止重复处理
                        // 实际中应该从外部系统来获取订单是否给出评价，此处仅仅使用函数进行模拟
                        Iterator<Entry<String, Long>> iterator = mapState.iterator();
                        while (iterator.hasNext()) {
                            Entry<String, Long> order = iterator.next();

                            // !!!: 一定要判断订单是否超时，否则可能导致较早的已超时订单触发时，将较晚的还未超时的订单一并处理了，
                            // 如果订单还未超时就不做处理
                            if ( order.getValue() + timeout > timestamp) {
                                continue;
                            }

                            String message = String.format("%s: 订单[id:%s,time:%s] - ",
                                    Util.ts2ds(System.currentTimeMillis()), order.getKey(),
                                    Util.ts2ds(order.getValue()));

                            if (isFavComment(order.getKey())) {
                                message += "用户已经作出评价.";
                            } else {
                                message += "系统自动好评.";
                            }
                            iterator.remove();
                            mapState.remove(order.getKey());
                            out.collect(message);
                        }
                    }

                    // 模拟订单是否已经给出评价
                    private boolean isFavComment(String orderId) {
                        return orderId.hashCode() % 3 == 0;
                    }
                });

        // 4: Sink
        resultStream.printToErr();
        // 5: Executed
        env.execute();
    }
}
