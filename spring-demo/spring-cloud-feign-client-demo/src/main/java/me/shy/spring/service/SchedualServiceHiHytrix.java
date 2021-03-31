/**
 * @Date        : 2020-11-11 15:06:54
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : -
 */
package me.shy.spring.service;

import org.springframework.stereotype.Component;

// 已 Component 形式注入
@Component
public class SchedualServiceHiHytrix implements SchedualServiceHi {

    @Override
    public String sayHiFromClientOne(String name) {
        return String.format("hi, %s, error!", name);
    }

}
