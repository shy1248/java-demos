/**
 * @Date        : 2021-03-31 21:47:10
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : Flink table api 完成订单信息统计
 */
package me.shy.flink.table;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.Tumble;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.types.Row;

import static org.apache.flink.table.api.Expressions.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class TableDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        EnvironmentSettings settings = EnvironmentSettings.newInstance().useBlinkPlanner().inStreamingMode().build();
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env, settings);

        env.setParallelism(1);

        DataStreamSource<Order> dataSource = env.addSource(new SourceFunction<Order>() {
            private static final long serialVersionUID = 1L;
            boolean isRuning = true;

            @Override
            public void run(SourceContext<Order> ctx) throws Exception {
                Random random = new Random();
                while (isRuning) {
                    String orderId = UUID.randomUUID().toString();
                    int userId = random.nextInt(4) + 1;
                    int money = random.nextInt(49) + 50;
                    Long createdTime = System.currentTimeMillis();
                    Order order = new Order(orderId, userId, money, createdTime);
                    System.out.println(String.format("Source emit an order: %s", order));
                    ctx.collect(order);
                    TimeUnit.MILLISECONDS.sleep(300);
                }
            }

            @Override
            public void cancel() {
                isRuning = false;
            }
        });

        // 需求：使用事件时间，每隔5秒钟统计过去5秒钟内各个用户的订单的总数、最大的订单金额、最小的订单金额
        // 由于要用到事件时间，因此需要注册时间戳和 watermark
        SingleOutputStreamOperator<Order> dataStreamWithWatermark = dataSource
                .assignTimestampsAndWatermarks(WatermarkStrategy.<Order>forBoundedOutOfOrderness(Duration.ofSeconds(5))
                        .withTimestampAssigner((order, temestamp) -> order.getCreatedTime()));
        // 创建表，rowtime() 标记该列为事件时间
        Table tableOrder = tableEnv.fromDataStream(dataStreamWithWatermark, $("orderId"), $("userId"), $("money"),
                $("createdTime").rowtime());

        // 输出表约束
        // tableOrder.printSchema();

        // table api
        Table select = tableOrder.window(Tumble.over(lit(5).second()).on($("createdTime")).as("w"))
                .groupBy($("userId"), $("w")).select($("userId").as("user"), $("orderId").count().as("total"),
                        $("money").max().as("max"), $("money").min().as("min"));

        // sql api
        // String sql = String.join(System.lineSeparator(),
        //         "select userId,count(orderId) as total,max(money) as maxMoney,min(money) as minMoney from "
        //                 + tableOrder + " ",
        //         "group by userId, tumble(createdTime, interval '5' second)");
        // Table select = tableEnv.sqlQuery(sql);

        // 创建临时视图
        // tableEnv.createTemporaryView("t_order", dataStreamWithWatermark, $("orderId"), $("userId"), $("money"),
        //         $("createdTime").rowtime());

        // sql api
        // String sql = String.join(System.lineSeparator(),
        //         "select userId,count(orderId) as total,max(money) as maxMoney,min(money) as minMoney from t_order ",
        //         "group by userId, tumble(createdTime, interval '5' second)");
        // Table select = tableEnv.sqlQuery(sql);

        // 如果需要在控制台输出，需要将 Table 转为 DataStream。Table 转为 DataStream 有 2 种：
        // toAppendStream：用于动态表只有 insert 操作时
        // tableEnv.toAppendStream(select, Row.class).printToErr();
        // toRetractStream：常用，当有数据删除时前边会有 false 标注；有数据增加时前边会有 true 标注
        tableEnv.toRetractStream(select, Row.class).printToErr();

        env.execute();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Order {
        private String orderId;
        private int userId;
        private int money;
        private Long createdTime;

        @Override
        public String toString() {
            return String.format("[orderId=%s,userId=%d,money=%d,time=%s]", orderId, userId, money,
                    DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss")
                            .format(Instant.ofEpochMilli(createdTime).atZone(ZoneId.systemDefault())));
        }
    }
}
