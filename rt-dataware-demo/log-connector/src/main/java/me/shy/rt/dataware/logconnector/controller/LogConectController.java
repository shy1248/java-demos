/**
 * @Date        : 2021-04-18 17:40:23
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : App 埋点日志收集 Controller
 */

package me.shy.rt.dataware.logconnector.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class LogConectController {

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @RequestMapping(path = "/logConnect")
    public String logConnect(@RequestParam("log") String jsonLog, @RequestParam("topic") String topic) {
        try {
            log.info(jsonLog);
            kafkaTemplate.send(topic, jsonLog);
            return "SUCCESS";
        } catch (Exception e) {
            e.printStackTrace();
            return "FAILED";
        }
    }
}
