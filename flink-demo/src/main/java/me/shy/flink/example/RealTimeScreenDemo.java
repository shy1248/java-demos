/**
 * @Date        : 2021-04-04 00:21:58
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 模拟双十一实时大屏，需求为实时统计每天的商品销售总额以及按照品类销售额的 top3，每秒钟更新一次
 */
package me.shy.flink.example;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.google.gson.Gson;

import org.apache.flink.api.common.RuntimeExecutionMode;
import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.common.state.BroadcastState;
import org.apache.flink.api.common.state.MapStateDescriptor;
import org.apache.flink.api.common.state.ReadOnlyBroadcastState;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.runtime.state.filesystem.FsStateBackend;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.datastream.BroadcastStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.CheckpointConfig.ExternalizedCheckpointCleanup;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.co.BroadcastProcessFunction;
import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.triggers.ContinuousProcessingTimeTrigger;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.util.Collector;

import me.shy.flink.example.pojo.Order;
import me.shy.flink.example.util.JDBCTool;
import me.shy.flink.example.util.Util;

public class RealTimeScreenDemo {
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

        // 3: Broadcast Source
        // 广播流
        // 构建状态描述器，由于 broadcast 中必须使用 MapState，所以此处构建一个 MapStateDescriptor，该 MapState 没有 key（也可以随便指定一个）
        // 此处直接使用 null，其值为另一个品类信息的 Map（key为品类ID，value为品类名称）集合，方便订单流中直接通过品类 ID 获取到品类名称
        MapStateDescriptor<Void, Map<Integer, String>> descriptor = new MapStateDescriptor<>("category", Types.VOID,
                Types.MAP(Types.INT, Types.STRING));
        // 每隔 1 秒中从 MySQL 中查询出所有的商品类别信息，广播至订单流中，用来更新 Top3 的品类名称
        BroadcastStream<Map<Integer, String>> broadcast = env
                .addSource(new RichParallelSourceFunction<Map<Integer, String>>() {
                    private static final long serialVersionUID = 1L;
                    boolean flag = true;
                    Connection connection = null;

                    @Override
                    public void open(Configuration parameters) throws Exception {
                        connection = JDBCTool.connect();
                    }

                    @Override
                    public void run(SourceContext<Map<Integer, String>> ctx) throws Exception {
                        while (flag) {
                            Map<Integer, String> categories = new HashMap<>();
                            JDBCTool.query(connection, "select id, name from t_category", null,
                                    new JDBCTool.QueryCallback() {
                                        @Override
                                        public void callback(ResultSet rs) throws SQLException {
                                            while (rs.next()) {
                                                categories.put(rs.getInt(1), rs.getString(2));
                                            }
                                            ctx.collect(categories);
                                            rs.close();
                                        }
                                    });
                            TimeUnit.SECONDS.sleep(1);
                        }
                    }

                    @Override
                    public void cancel() {
                        flag = false;
                        JDBCTool.close(connection);
                    }

                }, "MySQL source").broadcast(descriptor);

        // 4: Transmissions
        // 计算当天零点截止到当前时间的销售总额，每秒钟更新一次
        // 计算各个商品分类的 top3，每秒钟更新一次
        // 定义一个窗口，时间长度为 1 天，自定义该窗口的触发器，设置为每秒钟触发一次
        // flink 中的窗口（未给定时间偏移量）时间默认都是从正点开始的，使用的是 UTC 时间，因此，窗口定义需要指定偏移量，早8小时，此处使用的是 eventTime，无需设置偏移量
        SingleOutputStreamOperator<Tuple3<String, String, BigDecimal>> totalAmountByCategoryStream = orderStream
                // 连接广播流
                .connect(broadcast)
                // 处理连接流
                .process(new BroadcastProcessFunction<Order, Map<Integer, String>, Tuple2<String, BigDecimal>>() {
                    private static final long serialVersionUID = 1L;

                    // 处理广播流，主要用于更新广播状态中的值
                    @Override
                    public void processBroadcastElement(Map<Integer, String> in,
                            BroadcastProcessFunction<Order, Map<Integer, String>, Tuple2<String, BigDecimal>>.Context ctx,
                            Collector<Tuple2<String, BigDecimal>> out) throws Exception {
                        BroadcastState<Void, Map<Integer, String>> broadcastState = ctx.getBroadcastState(descriptor);
                        // 清空广播状态
                        broadcastState.clear();
                        // 将信息的品类信息状态值存入值广播状态中，此处的 in 即为每隔一秒从 MySQL 从查询出来的品类信息
                        // 因此，如果中途人为的改变了 MySQL 中品类信息（如品类名称），那么流中是可以实时感知的，因此广播流也常用于流中配置的实时变更
                        broadcastState.put(null, in);
                    }

                    // 处理事实流
                    @Override
                    public void processElement(Order value,
                            BroadcastProcessFunction<Order, Map<Integer, String>, Tuple2<String, BigDecimal>>.ReadOnlyContext ctx,
                            Collector<Tuple2<String, BigDecimal>> out) throws Exception {
                        // 获取广播流中的状态
                        ReadOnlyBroadcastState<Void, Map<Integer, String>> broadcastState = ctx
                                .getBroadcastState(descriptor);
                        if (null != broadcastState) {
                            // 从广播状态中获取值，此处即为品类信息的 Map 集合
                            Map<Integer, String> categories = broadcastState.get(null);
                            if (null != categories) {
                                out.collect(Tuple2.of(categories.get(value.getCategoryId()), value.getAmount()));
                            }
                        }
                    }
                })
                // 按照品类分组
                .keyBy(t -> t.f0)
                // 定义窗口，由于需求时要统计每天0点截止当前的总销售额与各品类的 top3，因此窗口时间定义为1天
                // 由于中国的时间比 UTC 时间早8个小时，所以窗口的偏移量设置为-8（否则就是UTC时间的0点开始）
                .window(TumblingProcessingTimeWindows.of(Time.days(1), Time.hours(-8)))
                // 由于需求中要每秒中更新一个结果，所以自定义窗口的触发时间间隔为1秒钟
                .trigger(ContinuousProcessingTimeTrigger.of(Time.seconds(1)))
                // 使用 aggregate 来进行复杂的窗口聚合计算
                .aggregate(new AggregateFunction<Tuple2<String, BigDecimal>, BigDecimal, BigDecimal>() {
                    private static final long serialVersionUID = -249327941910779409L;
                    BigDecimal bigDecimalAccumulator = new BigDecimal(0);

                    // 初始化累加器，只执行一次
                    @Override
                    public BigDecimal createAccumulator() {
                        return bigDecimalAccumulator;
                    }

                    // subTask 中的消息的聚合实现
                    @Override
                    public BigDecimal add(Tuple2<String, BigDecimal> value, BigDecimal accumulator) {
                        return accumulator.add(value.f1);
                    }

                    // 返回累加器的结果
                    @Override
                    public BigDecimal getResult(BigDecimal accumulator) {
                        return accumulator;
                    }

                    // 各个 subTask 之间的结果合并
                    @Override
                    public BigDecimal merge(BigDecimal a, BigDecimal b) {
                        return a.add(b);
                    }

                }, new WindowFunction<BigDecimal, Tuple3<String, String, BigDecimal>, String, TimeWindow>() {
                    private static final long serialVersionUID = 1L;

                    // AggregateFunction 将聚合的结果传递给 WindowFunction，WindowFunction 做最终的输出处理
                    // 其实可以直接在 AggregateFunction 中处理
                    @Override
                    public void apply(String category, TimeWindow window, Iterable<BigDecimal> input,
                            Collector<Tuple3<String, String, BigDecimal>> out) throws Exception {
                        BigDecimal totalAmountByCategory = input.iterator().next();
                        String dateTime = Util.ts2ds(System.currentTimeMillis());
                        // tuple3 ==> (时间，品类ID，当前品类的销售额汇总)
                        out.collect(Tuple3.of(dateTime, category, totalAmountByCategory));
                    }
                });

        // totalAmountByCategoryStream.printToErr();
        // 以上计算完毕后就是按每个品类的销售总额的统计，下面进行总的销售额（各品类相加）以及按品类销售额的 top3 计算
        SingleOutputStreamOperator<String> resultStream = totalAmountByCategoryStream.keyBy(t -> t.f0)
                .window(TumblingProcessingTimeWindows.of(Time.seconds(1)))
                .process(new ProcessWindowFunction<Tuple3<String, String, BigDecimal>, String, String, TimeWindow>() {
                    private static final long serialVersionUID = -1656130907955329225L;

                    @Override
                    public void process(String key,
                            ProcessWindowFunction<Tuple3<String, String, BigDecimal>, String, String, TimeWindow>.Context ctx,
                            Iterable<Tuple3<String, String, BigDecimal>> in, Collector<String> out) throws Exception {
                        // 借助优先级队列（小顶堆）实现品类销售额 top3
                        // 因为是 top3 只需要长度为 3 的队列即可，队列的优先级为从小到大的顺序
                        Queue<Tuple3<String, String, BigDecimal>> heap = new PriorityQueue<Tuple3<String, String, BigDecimal>>(
                                3, (t1, t2) -> t1.f2.compareTo(t2.f2));
                        // 销售总额
                        BigDecimal totalAmountByAllCategory = new BigDecimal("0");
                        // 迭代处理输入的消息，也即按品类汇总的信息
                        for (Tuple3<String, String, BigDecimal> t : in) {
                            // 计算总销售额，各品类的总金额进行累加
                            totalAmountByAllCategory = totalAmountByAllCategory.add(t.f2);
                            // 更新 top3 的品类
                            if (heap.size() < 3) {
                                // 如果队列中不足 3 个品类，直接加入队列
                                heap.add(t);
                            } else {
                                // 如果队列中已有 3 个品类，则取出队列中的第一个品类，该品类一定是已有 3 个品类中销售额最低的（优先级保证）
                                Tuple3<String, String, BigDecimal> littest = heap.peek();
                                if (t.f2.compareTo(littest.f2) == 1) {
                                    // 移除队列中销售额最小的品类
                                    heap.poll();
                                    // 将当前输入的品类更新进 top3 队列中
                                    heap.add(t);
                                }
                            }
                        }
                        // 可以使用 out 将汇总的信息发布出去，然后使用 sink 存储或者输出至任何想要的地方，此处只做简单的输出
                        // top3 队列中是顺序的，因此需要转为逆序，并格式化输出信息
                        List<String> top3List = heap.stream().sorted((t1, t2) -> t1.f2.compareTo(t2.f2) == 1 ? -1 : 1)
                                .map(t -> String.format("%s[%s元]", t.f1, t.f2.toString())).collect(Collectors.toList());
                        String top3 = String.join(",", top3List);
                        String output = String.format("时间：%s === 销售总额：%s元 === Top3品类：%s", key,
                                totalAmountByAllCategory.toString(), top3);
                        // System.err.println(output);
                        out.collect(output);
                    }

                });

        // 5: Sink
        resultStream.printToErr();
        // 6: Executed
        env.execute();
    }
}
