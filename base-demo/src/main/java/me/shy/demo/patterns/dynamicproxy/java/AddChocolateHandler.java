/**
 * @Since: 2019-07-26 09:20:28
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 * @LastTime: 2019-07-26 10:27:11
 */

package me.shy.demo.patterns.dynamicproxy.java;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class AddChocolateHandler implements InvocationHandler {
    private Object obj;

    public AddChocolateHandler(Object obj) {
        this.obj = obj;
    }

    @Override public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object res = null;
        if (method.getName().equals("make")) {
            System.out.println("Before make cake...");
            res = method.invoke(this.obj, args);
            System.out.println("After make cake...");
            System.out.println("Add chocolate on cake!");
        } else if (method.getName().equals("show")) {
            System.out.println("Before show...");
            res = method.invoke(this.obj, args);
            System.out.println("After show...");
        } else {
            res = method.invoke(this.obj, args);
        }

        return res;
    }

}

