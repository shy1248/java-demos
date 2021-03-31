/**
 * @Date        : 2020-11-11 21:24:16
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : -
 */
package me.shy.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
// 开启 Eureka 客户端支持
@EnableEurekaClient
@EnableDiscoveryClient
// 开启 zuul 网管代理
@EnableZuulProxy
public class DemoZuulApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoZuulApplication.class, args);
    }
}
