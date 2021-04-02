/**
 * @Date        : 2021-04-01 23:40:22
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 直接使用 Table 连接 Kafka
 */
package me.shy.flink.table;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

public class TableConnectorDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        EnvironmentSettings envSettings = EnvironmentSettings.newInstance().useBlinkPlanner().inStreamingMode().build();
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env, envSettings);

        // 0000001, 1, 4
        // 0000002, 2, 3
        // 0000003, 1, 5
        // 0000004, 1, 7
        // 0000005, 2, 9
        // 0000006, 1, 1
        // 0000007, 1, 6
        // 0000008, 2, 11

        String sourceTableDescribe = String.join(System.lineSeparator(),
                "CREATE TABLE kafka_source_demo (",
                "  `event_time` TIMESTAMP(3) METADATA FROM 'timestamp',",
                "  `order_id` STRING,",
                "  `user_id` INT,",
                "  `amount` INT,",
                " WATERMARK FOR event_time AS event_time - INTERVAL '5' SECOND",
                ") WITH (",
                "  'connector' = 'kafka',",
                "  'topic' = 'flink-table-source-demo',",
                "  'properties.bootstrap.servers' = 'kafka-svc:9092',",
                "  'properties.group.id' = 'flink-demo',",
                "  'scan.startup.mode' = 'earliest-offset',",
                "  'value.format' = 'csv'", ")");
        tableEnv.executeSql(sourceTableDescribe);

        String sinkTableDescribe = String.join(System.lineSeparator(),
                "CREATE TABLE kafka_sink_demo (",
                "  `user_id` INT,",
                "  `total` BIGINT,",
                "  `max_amount` INT,",
                "  `min_amount` INT",
                ") WITH (",
                "  'connector' = 'kafka',",
                "  'topic' = 'flink-table-sink-demo',",
                "  'properties.bootstrap.servers' = 'kafka-svc:9092',",
                "  'properties.group.id' = 'flink-demo',",
                "  'scan.startup.mode' = 'earliest-offset',",
                "  'value.format' = 'csv'", ")");
        tableEnv.executeSql(sinkTableDescribe);

        // kafka sink table only suport append aggregate
        tableEnv.executeSql(
                "insert into kafka_sink_demo select user_id, count(order_id), max(amount), min(amount) from kafka_source_demo group by TUMBLE(event_time, INTERVAL '10' SECOND), user_id");

        // not needed: java.lang.IllegalStateException: No operators defined in streaming topology. Cannot generate StreamGraph.
        // env.execute("Table-connector-demo");
        // tableEnv.execute("Table-connector-demo");
    }
}
