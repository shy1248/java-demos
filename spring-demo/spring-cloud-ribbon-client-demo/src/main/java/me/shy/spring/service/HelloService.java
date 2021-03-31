/**
 * @Date        : 2020-10-31 15:30:37
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : -
 */
package me.shy.spring.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HelloService {
    // project: spring-cloud-eureka-client-demo
    public static final String SERVICE_NAME = "http://SERVICE-HI";

    @Autowired
    private RestTemplate restTemplate;

    // 对该方法创建了熔断器的功能，并指定了 fallbackMethod 熔断方法
    @HystrixCommand(fallbackMethod = "hiError")
    public String hiService(String name) {
        // 通过服务名直接调用服务
        return restTemplate.getForObject(SERVICE_NAME + "/hi?name=" + name, String.class);
    }

    // 熔断方法
    public String hiError(String name) {
        return String.format("hi, %s, error!", name);
    }
}
