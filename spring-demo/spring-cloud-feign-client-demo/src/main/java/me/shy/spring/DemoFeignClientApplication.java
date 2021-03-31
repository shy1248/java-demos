/**
 * @Date        : 2020-11-10 16:14:27
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : Feign client
 *
 * 1.定义一个 feign 接口，通过注解 @FeignClient(value="service-hi") 指定需要调用的服务
 */

package me.shy.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
// 开启 Eureka 客户端支持
@EnableEurekaClient
@EnableDiscoveryClient
// 开启 Feign 客户端支持
@EnableFeignClients
public class DemoFeignClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoFeignClientApplication.class, args);
    }
}
