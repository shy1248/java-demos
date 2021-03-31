package me.shy.dubbo;

/**
 * @Since: 2020/5/12 16:00
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 *
 **/
public class DemoServiceImpl implements DemoService {
    @Override public String sayHello(String name) {
        return String.format("Hello, %s!", name);
    }
}
