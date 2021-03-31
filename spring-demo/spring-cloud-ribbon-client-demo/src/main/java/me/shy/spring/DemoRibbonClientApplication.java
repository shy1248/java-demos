/**
 * @Date        : 2020-10-31 15:05:02
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : Ribbon 是一个支持负载均衡的 Restful 客户端
 *
 * 一个服务注册中心，eureka server,端口为8761
 * service-hi工程跑了两个实例，端口分别为8001,8002，分别向服务注册中心注册
 * sercvice-ribbon端口为8101,向服务注册中心注册
 * 当sercvice-ribbon通过restTemplate调用service-hi的hi接口时，因为用ribbon进行了负载均衡，会轮流的调用service-hi：8001 和 8002 两个端口的hi接口；
 *
 */
package me.shy.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
// 开启 eureka 客户端支持
@EnableEurekaClient
@EnableDiscoveryClient
// 开启 Hystrix 支持
@EnableHystrix
public class DemoRibbonClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoRibbonClientApplication.class, args);
    }

    // 注入 RestTemplate 客户端
    @Bean
    // 开启 RestTemplate 的负载均衡功能
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
