/**
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Licence: GPLv3
 * @Description: -
 * @Since: 2019-04-25 22:20:54
 * @LastTime: 2019-05-13 10:42:18
 */

package me.shy.demo.storm.kafka;

import java.util.Arrays;

import me.shy.demo.storm.utils.DemosSubmitter;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.storm.Config;
import org.apache.storm.kafka.spout.KafkaSpout;
import org.apache.storm.kafka.spout.KafkaSpoutConfig;
import org.apache.storm.kafka.spout.KafkaSpoutConfig.FirstPollOffsetStrategy;
import org.apache.storm.kafka.spout.KafkaSpoutConfig.ProcessingGuarantee;
import org.apache.storm.topology.TopologyBuilder;

public class KafkaTopoDemo {
    public static void main(String[] args) {
        final String topic = "kafka_java_demo";
        final String bootstrap_servers = "demo.shy.com:9092";

        KafkaSpoutConfig<String, String> spoutConfig = KafkaSpoutConfig.builder(bootstrap_servers, Arrays.asList(topic))
                .setProp(ConsumerConfig.GROUP_ID_CONFIG, "kafkaSpoutDemoGroup")
                .setProp(ConsumerConfig.AUTO_OFFSET_RESET_DOC, "earliest")
                .setProp(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 300000)
                .setProp(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                        "org.apache.kafka.common.serialization.StringDeserializer")
                .setProp(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                        "org.apache.kafka.common.serialization.StringDeserializer")
                .setProp(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, 200)
                .setOffsetCommitPeriodMs(10000)
                .setFirstPollOffsetStrategy(FirstPollOffsetStrategy.EARLIEST)
                .setProcessingGuarantee(ProcessingGuarantee.NO_GUARANTEE)
                .setMaxUncommittedOffsets(250)
                .build();
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("kafka_spout", new KafkaSpout<String, String>(spoutConfig), 1);
        builder.setBolt("kafka_comsume_bolt", new KafkaBoltDemo(), 1).shuffleGrouping("kafka_spout");

        Config conf = new Config();
        conf.setDebug(false);
        // conf.put(Config.STORM_ZOOKEEPER_SESSION_TIMEOUT, 400000);
        DemosSubmitter.localSubmit("kafka_demo_topo", conf, builder.createTopology());
    }
}
