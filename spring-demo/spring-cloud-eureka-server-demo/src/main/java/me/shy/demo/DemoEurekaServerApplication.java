/**
 * @Date        : 2020-10-22 14:40:56
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : -
 */

package me.shy.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

// 表明该服务是一个 Eureka
@EnableEurekaServer
@SpringBootApplication
public class DemoEurekaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoEurekaServerApplication.class, args);
    }
}
