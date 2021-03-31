package me.shy.spring.aop.advice;

import java.lang.reflect.Method;
import org.springframework.aop.ThrowsAdvice;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 *
 * 方法名必须叫：afterThrowing
 * 方法参数类型必须为下面2种形式
 */
public class ThrowAdviceDemo implements ThrowsAdvice {

    public void afterThrowing(RuntimeException re) {
        System.out.println("Pointcut throws exception: " + re.getMessage() + ".[Schema-based]");
    }

    public void afterThrowing(Method method, Object[] args, Object target, RuntimeException re) {
        System.out.println("Pointcut throws exception: " + re.getMessage() + ".[Schema-based]");
        // 切点的方法名
        System.out.println("Pointcut method is: " + method.getName());
        // 切点的方法参数
        if (args.length != 0) {
            for (Object o : args) {
                System.out.println("Pointcut method's args is: " + o.toString());
            }
        } else {
            System.out.println("Pointcut method has no any arguments.");
        }
        // 切点所在的类
        System.out.println("Pointcut's class is: " + target.getClass());
    }
}
