/**
 * @Date        : 2021-02-14 16:25:16
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : -
 */
package me.shy.demo.proxy.dynamics.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class LoggedProxyHandler implements InvocationHandler {
    Moveable m;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(String.format("[%s]: Starting moved ...", System.currentTimeMillis()));
        Object o = method.invoke(m, args);
        System.out.println(String.format("[%s]: Stop moved.", System.currentTimeMillis()));
        return o;
    }

    public LoggedProxyHandler(Moveable m) {
        this.m = m;
    }

}
