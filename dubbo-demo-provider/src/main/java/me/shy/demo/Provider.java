package me.shy.demo;

import java.io.IOException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Since: 2020/5/12 16:12
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 *
 **/
public class Provider {
    public static void main(String[] args) throws IOException {
        System.setProperty("java.net.perferIPv4Stack", "true");
        // 从配置文件获取spring的Context对象
        ClassPathXmlApplicationContext context =
            new ClassPathXmlApplicationContext(new String[] {"META-INF/spring/dubbo-demo-provider.xml"});
        // 启动context
        context.start();
        System.out.println("Provider started! Press any key to exit...");
        // 按任意键退出
        System.in.read();
    }
}
