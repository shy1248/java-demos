/**
 * @Date : 2021-03-29 10:04:49
 * @Author : shy
 * @Email : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version : v1.0
 * @Description :
 *
 *              模拟实时订单数据,格式为: (订单ID，用户ID，订单金额，时间戳/事件时间) 要求每隔5s,计算5秒内(基于时间的滚动窗口)，每个用户的订单总金额
 *              并添加Watermaker来解决一定程度上的数据延迟和数据乱序问题。
 *
 */
package me.shy.flink.watermark;

import java.time.Duration;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.flink.api.common.RuntimeExecutionMode;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.OutputTag;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class SideCarDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setRuntimeMode(RuntimeExecutionMode.AUTOMATIC);
        DataStreamSource<Order> dataSource = env.addSource(new SourceFunction<Order>() {
            private static final long serialVersionUID = 6870449465833737932L;
            boolean flag = true;
            Random random = new Random();

            @Override
            public void run(SourceContext<Order> ctx) throws Exception {
                while (flag) {
                    String oid = UUID.randomUUID().toString();
                    int uid = random.nextInt(3) + 1;
                    int money = random.nextInt(101) + 50;
                    long eventTime = System.currentTimeMillis() - random.nextInt(10) * 1000;
                    ctx.collect(new Order(oid, uid, money, eventTime));
                    TimeUnit.MILLISECONDS.sleep(500);
                }
            }

            @Override
            public void cancel() {
                flag = false;
            }

        });

        // 创建一个 outputTag，用于标记严重延迟的数据
        OutputTag<Order> lateness = new OutputTag<Order>("LatenessData", Types.POJO(Order.class));
        // 注册 watermark 以及事件时间
        SingleOutputStreamOperator<Order> dataWithWatermark =
                dataSource.assignTimestampsAndWatermarks(
                        WatermarkStrategy.<Order>forBoundedOutOfOrderness(Duration.ofSeconds(3))
                                .withTimestampAssigner((order, ts) -> order.getEventTime()));
        // 开始处理并记录严重的数据到 outputTag 中，watermark 最大延时容忍时间为 3s，因此延时超过 3s 的数据将被记录到 outputTag 中
        SingleOutputStreamOperator<Order> sum = dataWithWatermark.keyBy(order -> order.getUid())
                .window(TumblingEventTimeWindows.of(Time.seconds(5)))
                .allowedLateness(Time.seconds(3)).sideOutputLateData(lateness).sum("money");
        // 获取严重延时的数据
        DataStream<Order> latenessData = sum.getSideOutput(lateness);

        // 输出正常
        sum.printToErr("Nomal data");
        // 输出严重延迟数据
        latenessData.printToErr("Lateness data");

        env.execute();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Order {
        private String oid;
        private int uid;
        private int money;
        private long eventTime;
    }
}
