/**
 * @Date        : 2021-02-14 16:24:49
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : -
 */
package me.shy.dp.proxy.dynamics.jdk;

import java.lang.reflect.Proxy;

public class TestClient {
    public static void main(String[] args) {
        // 调用被代理类自身的方法
        Tank tank = new Tank();
        tank.move(10);

        // 使用 JDK 的动态代理生成 Tank 类的代理实例
        Moveable m = (Moveable) Proxy.newProxyInstance(tank.getClass().getClassLoader(),
                tank.getClass().getInterfaces(), new LoggedProxyHandler(tank));
        m.move(100);
    }
}
