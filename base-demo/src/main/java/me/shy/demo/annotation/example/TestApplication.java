/**
 * @Date        : 2020-10-18 21:22:14
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description :
 *
 * 自定义注解结合 Spring 实现如下场景：
 *
 * 1.结合拦截器，实现登陆验证；
 * 2.结合切面，实现给方法前后增加日志。
 *
 */
package me.shy.demo.annotation.example;

import javax.validation.constraints.NotNull;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class TestApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

    // 登陆验证：无需登陆可以访问
    @GetMapping("/a")
    public String a() {
        return "Hello, stranger, you resource A ...";
    }

    // 登陆验证：需要登陆后才能访问
    @LoginRequired
    @GetMapping("/b")
    public String b() {
        return "Hello, stranger, you resource B ...";
    }

    // Aspect实现方法前后增加日志
    @MyLog
    @GetMapping("/c/{who}")
    public String c(@PathVariable("who") @NotNull String name) {
        if ("err".equals(name)) {
            throw new RuntimeException("A test Error.");
        }
        return "Hello, " + name + ", you got resource C ...";
    }
}
