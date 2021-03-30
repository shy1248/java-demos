/**
 * @Date : 2021-03-28 18:21:40
 * @Author : shy
 * @Email : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version : v1.0
 * @Description : -
 */
package me.shy.demo.watermark;

import java.time.Duration;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.apache.flink.api.common.RuntimeExecutionMode;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class WatermarkDemo {
    public static void main(String[] args) throws Exception {
        // 0: set env
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setRuntimeMode(RuntimeExecutionMode.AUTOMATIC);
        // 1: generate data source
        DataStreamSource<Order> dataSource = env.addSource(new SourceFunction<Order>() {
            private static final long serialVersionUID = 1L;
            boolean flag = true;
            Random random = new Random();

            @Override
            public void run(SourceContext<Order> ctx) throws Exception {
                while (flag) {
                    String oid = UUID.randomUUID().toString();
                    int uid = random.nextInt(4) + 1;
                    int money = random.nextInt(101) + 50;
                    // 模拟乱序
                    long eventTime = System.currentTimeMillis() - random.nextInt(5) * 1000;
                    Order order = new Order(oid, uid, money, eventTime);
                    System.err.println("Emit an order: " + order);
                    ctx.collect(order);
                    TimeUnit.SECONDS.sleep(1);
                }
            }

            @Override
            public void cancel() {
                flag = false;
            }
        }, Types.POJO(Order.class));

        // 3: transform
        // 老 api，设置处理时间为时间源时间
        // env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        // 新 api, 设置 watermark，最大容忍的延时时间为 5s，将 Order 中的 eventTime 作为时间处理时间
        // watermark >= 窗口结束时间  ==> 触发窗口计算
        // watermark = 当前窗口最大事件时间 - 容忍的延时时间
        // 当前窗口最大事件时间 >= 窗口结束时间 + 容忍的延时时间 ==> 触发窗口计算
        SingleOutputStreamOperator<Order> sum = dataSource
                .assignTimestampsAndWatermarks(
                        WatermarkStrategy.<Order>forBoundedOutOfOrderness(Duration.ofSeconds(5))
                                .withTimestampAssigner((order, timestamp) -> order.getEventTime()))
                .keyBy(order -> order.getUid()).window(TumblingEventTimeWindows.of(Time.seconds(5)))
                .sum("money");

        // 4: sink
        sum.printToErr();

        // 5: execute
        env.execute();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Order {
        private String oid;
        private Integer uid;
        private Integer money;
        private Long eventTime;
    }
}
