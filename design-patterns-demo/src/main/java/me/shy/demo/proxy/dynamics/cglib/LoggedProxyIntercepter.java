/**
 * @Date        : 2021-02-14 16:42:12
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : -
 */
package me.shy.demo.proxy.dynamics.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class LoggedProxyIntercepter implements MethodInterceptor {

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println(String.format("[%s]: Starting moved ...", System.currentTimeMillis()));
        Object o = proxy.invokeSuper(obj, args);
        System.out.println(String.format("[%s]: Stop moved.", System.currentTimeMillis()));
        return o;
    }

}
