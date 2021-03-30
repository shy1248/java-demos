/**
 * @Since: 2019-12-07 12:58:00
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 * @LastTime: 2019-12-07 13:07:15
 */
package me.shy.demo.hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class HelloDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloDemoApplication.class, args);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String say() {
        return "Hello, Spring Boot!";
    }

}
