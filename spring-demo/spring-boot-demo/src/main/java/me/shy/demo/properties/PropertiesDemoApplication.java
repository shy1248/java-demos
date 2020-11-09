/**
 * @Since: 2019-12-07 13:08:53
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 * @LastTime: 2019-12-07 13:50:47
 */
package me.shy.demo.properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class PropertiesDemoApplication {

    @Autowired
    GirlPropertites girlPropertites;
    @Value(value = "${age}")
    private Integer age;
    @Value(value = "${cupSize}")
    private String cupSize;
    @Value(value = "${content}")
    private String content;

    public static void main(String[] args) {
        SpringApplication.run(PropertiesDemoApplication.class, args);
    }

    @RequestMapping(value = "/value", method = RequestMethod.GET)
    public String echo() {
        return age + " ==== " + cupSize;
    }

    @RequestMapping(value = "/refer", method = RequestMethod.GET)
    public String echoRefer() {
        return content;
    }

    @RequestMapping(value = "/prefix", method = RequestMethod.GET)
    public String echoPrefix() {
        return girlPropertites.getAge() + " ==== " + girlPropertites.getCupSize();
    }

}
