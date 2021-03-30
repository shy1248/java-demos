/**
 * @Date : 2021-03-29 19:26:46
 * @Author : shy
 * @Email : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version : v1.0
 * @Description : Flink checkpint
 *
 *              checkpoint 是某一时刻整个 flink 任务状态集合的快照，flink 容错机制之一是作业失败可以从 checkpoint 中恢复。 checkpoint 由 JM 定时发起，从 source 到
 *              sink 依此完成后结束；flink checkpoint 目前支持以下三种 checkpoint 状态后端： 1. MemeoryStateBackend: 基于内存的状态后端；State 存储在 TM
 *              的内存中，checkpoint 存储在 JM 的内存中，一般为测试时使用； 2. FsStateBackend：基于文件系统的状态后端；State 存储在 TM
 *              的内存中，checkpoint存储在指定的文件系统中（支持本地与HDFS），适合状态数据集较小的作业； 3. RocksDBStateBackend：基于 RocksDB 的状态后端；State 存储在
 *              RocksDB（RocksDB是一种基于内存与磁盘的 k-v数据库）中，checkpoint 存储在指定的文件系统中（支持本地与HDFS）
 *
 *              flink 容错机制之二是支持作业失败可以自动重启（有不同的重启机制）。 flink 目前支持以下几种自动重启策略：
 *              fixedDelayRestart：固定延时重启策略；需配置最多重启次数以及每次重启之间间隔时间；
 *              failureRateRestart：失败率重启策略；需配置在多长时间之内最多可重启多少次，每次重启之间的时间间隔； 如果没有显式指定重启策略，则采用以下策略： 如果未开启 checkpoint, 重启策略为
 *              None；如果开启了 checkpoint，默认为 fixedDelayRestart 策略，并且最大重启次数为 Integer.MAX_VALUE，每次重启时间间隔为 1 秒钟；
 */
package me.shy.demo.checkpoint;

import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.SystemUtils;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.runtime.state.filesystem.FsStateBackend;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.CheckpointConfig.ExternalizedCheckpointCleanup;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import org.apache.flink.util.Collector;

public class CheckpointDemo {
    public static void main(String[] args) {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // 设置并行度
        env.setParallelism(2);
        // 开启 checkpoint，并指定每秒钟做一次
        env.enableCheckpointing(1000);
        // 配置 checkpoint 的存储后端
        if (SystemUtils.IS_OS_WINDOWS) {
            env.setStateBackend(new FsStateBackend("d:/testbased/flink-demo/checkpoint"));
        } else {
            env.setStateBackend(new FsStateBackend("hdfs://hadoop-nn-svc:9000/flink/checkpoint"));
        }
        // 设置 checkpint 模式为 exactly-once，这是默认设置
        env.getCheckpointConfig().setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE);
        // 每 2 次 checkpoint 之间最少间隔 500ms，以防止上一个 checkpoint 还未完成就触发下一次 checkpoint
        env.getCheckpointConfig().setMinPauseBetweenCheckpoints(500);
        // checkpoint 超时时间为 1 分钟，如果 1 分钟内本次 checkpoint 未完成，则认为失败
        env.getCheckpointConfig().setCheckpointTimeout(60000);
        // 设置 checkpoint 的并行度
        env.getCheckpointConfig().setMaxConcurrentCheckpoints(1);
        // 设置当任务取消时 checkpoint 的保留策略，有以下2种策略：
        // DELETE_ON_CANCELLATION：任务取消时 checkpoint 被删除，意味着取消之后作业无法从 checkpoint 中恢复；
        // RETAIN_ON_CANCELLATION：任务取消时 checkpoint 不会被删除，需要手动删除
        // 注意：当任务以失败状态退出时不会被删除
        env.getCheckpointConfig().enableExternalizedCheckpoints(ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION);
        // 启用未对齐（一部分 checkpint 数据存储于缓冲区中）的 checkpoint，这将大大减少背压下的检查点时间，该选项只有 CheckpointingMode.EXACTLY_ONCE 时才能被设置
        env.getCheckpointConfig().enableUnalignedCheckpoints();
        // 设置重启策略为 fixedDelayRestart，最多重启 10 次，每次间隔 10 秒钟，由于并行度为 2，所以实际重启次数为 5
        env.setRestartStrategy(
            RestartStrategies.fixedDelayRestart(10, org.apache.flink.api.common.time.Time.of(10, TimeUnit.SECONDS)));
        // 设置重启策略为 failureRateRestart，如果 5 分钟之内重启未超过 3 次，则继续重启；否则作业失败终止；每次重启时间间隔为 10 秒钟
        // env.setRestartStrategy(RestartStrategies.failureRateRestart(3, Time.of(5, TimeUnit.MINUTES), Time.of(10, TimeUnit.SECONDS)));

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "kafka-svc:9092");
        properties.setProperty("group.id", "flink-demo");
        properties.setProperty("auto.offset.reset", "latest");
        //会开启一个后台线程每隔5s检测一下Kafka的分区情况
        properties.setProperty("flink.partition-discovery.interval-millis", "5000");
        // 关闭自动提交
        properties.setProperty("enable.auto.commit", "true");
        properties.setProperty("auto.commit.interval.ms", "2000");
        // properties.setProperty("enable.auto.commit", "false");
        properties.setProperty("offsets.commit.required.acks", "0");
        // TODO：当输入 bug 单词后，作业陷入无限重启直到到达重启次数后失败；
        // 作业失败后，有问题的 offset 已经被保存至 checkpoint 中，因此从 checkpoint 中恢复后一直失败
        // 解决办法：手动维护 offset
        FlinkKafkaConsumer<String> kafkaConsumer =
            new FlinkKafkaConsumer<String>("flink-source-demo", new SimpleStringSchema(), properties);
        // 关闭 offset 自动提交至 checkpoint
        // kafkaConsumer.setCommitOffsetsOnCheckpoints(false);
        // 只消费 topic：flink-source-demo 的 0 分区
        // KafkaTopicPartition topic = new KafkaTopicPartition("flink-source-demo", 0);
        // Map<KafkaTopicPartition, Long> specifiedOffsetMap = new HashMap<>();
        // kafkaConsumer.setStartFromSpecificOffsets(specifiedOffsetMap);
        // Long currentOffset = 0L;

        SingleOutputStreamOperator<String> wordCounts =
            env.addSource(kafkaConsumer).flatMap(new FlatMapFunction<String, String>() {
                private static final long serialVersionUID = -2917663196123089484L;

                @Override
                public void flatMap(String value, Collector<String> out) throws Exception {
                    if (null != value && !"".equals(value.trim())) {
                        String[] words = value.split(" ");
                        Arrays.stream(words).forEach(word -> out.collect(word));
                    }
                }
            }).map(new MapFunction<String, Tuple2<String, Integer>>() {

                private static final long serialVersionUID = 1L;

                @Override
                public Tuple2<String, Integer> map(String value) throws Exception {
                    // 模拟故障，观察重启机制
                    if (value.equals("bug")) {
                        throw new Exception("A bug trigged...");
                    }
                    return Tuple2.of(value, 1);
                }

            }).keyBy(t -> t.f0).window(TumblingProcessingTimeWindows.of(Time.seconds(5))).sum(1)
                .map(t -> String.format("%s:::%d", t.f0, t.f1));

        wordCounts.printToErr();
        // 如果没有异常发生，递增 offset
        // currentOffset += 1L;
        // specifiedOffsetMap.putIfAbsent(topic, currentOffset);
        wordCounts.addSink(new FlinkKafkaProducer<String>("flink-sink-demo", new SimpleStringSchema(), properties));

        try {
            env.execute("WordCountWithKafka");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
