/**
 * @Since: 2019-05-12 13:20:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Licence: GPLv3
 * @Description: -
 * @LastTime: 2019-05-12 15:53:41
 */

package me.shy.demo.kafka;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class ProducerDemo extends Thread {

  private Producer<String, String> producer = null;
  private String topic;
  private Map<String, Object> conf = new HashMap<String, Object>();

  public ProducerDemo(String topic) {
    this.conf.put("bootstrap.servers", "demo.shy.com:9092");
    this.conf.put("acks", "all");
    this.conf.put("retries", 0);
    this.conf.put("batch.size", 16384);
    this.conf.put("linger.ms", 1);
    this.conf.put("buffer.memory", 33554432);
    this.conf.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    this.conf.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    this.producer = new KafkaProducer<String, String>(conf);
    this.topic = topic;
  }

  public static void main(String[] args) {
    ProducerDemo pd = new ProducerDemo("kafka_java_demo");
    pd.start();
  }

  @Override
  public void run() {
    int messageNo = 1;
    while (true) {
      String message = new String("Message_" + messageNo);
      this.producer.send(new ProducerRecord<String, String>(this.topic, message));
      messageNo++;
      System.out.println("Publish: " + message + "...");
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        System.err.println("Thread[" + Thread.currentThread().getName()
            + "] has been interrupted. The reason is:\n" + e.getMessage());
      }
    }
  }

}
