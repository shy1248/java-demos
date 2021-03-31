/**
 * @Date        : 2020-11-10 16:35:58
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : -
 */
package me.shy.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import me.shy.spring.service.SchedualServiceHi;

@RestController
public class HiController {

    @Autowired
    SchedualServiceHi serviceHi;

    @GetMapping("/hi")
    public String hi(@RequestParam(value = "name") String name) {
        // 直接调用 Feign 客户端消费服务
        return serviceHi.sayHiFromClientOne(name);
    }
}
