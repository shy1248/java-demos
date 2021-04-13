/**
 * @Date        : 2021-04-11 17:50:51
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : -
 */
package me.shy.rt.dataware.demo.datamocker.actionlogmocker.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

public class KafkaUtil {
    @Autowired
    public static KafkaTemplate<String, String> KAFKA_TEMPLATE;

    public static void send(String message) {
        KAFKA_TEMPLATE.sendDefault(message);
    }

    public static void send(String topic, String message) {
        KAFKA_TEMPLATE.send(topic, message);
    }
}
