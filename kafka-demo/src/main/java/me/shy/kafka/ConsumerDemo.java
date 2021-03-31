/**
 * @Since: 2019-05-12 14:22:32
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Licence: GPLv3
 * @Description: -
 * @LastTime: 2019-05-12 16:22:26
 */
package me.shy.kafka;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class ConsumerDemo extends Thread {
    private Consumer<String, String> consumer = null;
    private String topic;
    private Properties props = new Properties();

    public ConsumerDemo(String topic) {
        this.props.put("bootstrap.servers", "demo.shy.com:9092");
        this.props.put("group.id", "java-kafka-demo-group");
        this.props.put("enable.auto.commit", "true");
        this.props.put("auto.commit.interval.ms", "1000");
        this.props.put("auto.offset.reset", "earliest");
        this.props.put("session.timeout.ms", "30000");
        this.props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        this.props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        this.consumer = new KafkaConsumer<String, String>(props);
        this.topic = topic;
        this.consumer.subscribe(Arrays.asList(this.topic));
    }

    public static void main(String[] args) {
        ConsumerDemo cd = new ConsumerDemo("kafka_java_demo");
        cd.start();
    }

    @Override public void run() {
        while (true) {
            ConsumerRecords<String, String> records = this.consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                System.out
                    .println("Consume message[offset=" + record.offset() + ", metadata=" + record.value() + "]...");
            }
        }
    }
}
