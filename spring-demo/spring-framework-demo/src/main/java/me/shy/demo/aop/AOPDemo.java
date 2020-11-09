package me.shy.demo.aop;

import org.springframework.stereotype.Component;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 */
@Component("aopDemo") public class AOPDemo {
    public boolean demo(final int id, final String content) {
        System.out.println("This is a Spring aop demo method out: [id=" + id + ",content=" + content + "]");
        // 异常通知测试
        // int i = 5 / 0;
        return true;
    }
}
