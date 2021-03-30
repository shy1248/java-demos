/**
 * @Since: 2019-07-26 10:59:12
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 * @LastTime: 2019-07-26 14:35:55
 */

package me.shy.demo.patterns.dynamicproxy.cglib;

import java.lang.reflect.Method;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class AddChocolateInterceptor implements MethodInterceptor {

    private Object obj;

    public AddChocolateInterceptor(Object obj) {
        this.obj = obj;
    }

    @Override public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("Before...");
        Object res = method.invoke(this.obj, args);
        System.out.println("After...");
        return res;
    }

}

