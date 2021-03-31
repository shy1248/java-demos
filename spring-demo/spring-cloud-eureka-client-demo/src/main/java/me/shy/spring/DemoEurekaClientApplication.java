/**
 * @Date        : 2020-10-23 22:05:33
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : -
 */

package me.shy.spring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class DemoEurekaClientApplication {
    @Value("${server.port}")
    private int port;

    public static void main(String[] args) {
        SpringApplication.run(DemoEurekaClientApplication.class, args);
    }

    @GetMapping("/hi")
    public String sayHi(@RequestParam(required = true) String name) {
        return String.format("Hi, %s, i'm from port: %d", name, port);
    }
}
