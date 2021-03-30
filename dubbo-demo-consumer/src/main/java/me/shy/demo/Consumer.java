package me.shy.demo;

import java.io.IOException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Since: 2020/5/12 16:36
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 *
 **/
public class Consumer {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context =
            new ClassPathXmlApplicationContext(new String[] {"META-INF/spring/dubbo-demo-consumer.xml"});
        context.start();
        System.out.println("Consumer started! Press any key to exit...");
        DemoService demoService = (DemoService)context.getBean("demoService");
        String resp = demoService.sayHello("Tom");
        System.out.println(resp);
        System.in.read();
    }
}
