/**
 * @Date        : 2021-02-14 16:24:49
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : -
 */
package me.shy.dp.proxy.dynamics.cglib;

import net.sf.cglib.proxy.Enhancer;

public class TestClient {
    public static void main(String[] args) {
        // 调用被代理类自身的方法
        Tank tank = new Tank();
        tank.move(10);

        // 使用 CGLIB 的动态代理生成 Tank 类的代理实例
        Enhancer enhancer = new Enhancer();
        // enhancer.setUseCache(true);
        // enhancer.setUseFactory(true);
        // 设置需要代理的类
        enhancer.setSuperclass(tank.getClass());
        // 设置代理类的回调类，会自动调用该类的
        enhancer.setCallback(new LoggedProxyIntercepter());
        Tank tankProxy = (Tank) enhancer.create();
        tankProxy.move(100);
    }
}
