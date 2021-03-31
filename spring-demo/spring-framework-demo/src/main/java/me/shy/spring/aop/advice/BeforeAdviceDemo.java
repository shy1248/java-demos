package me.shy.spring.aop.advice;

import java.lang.reflect.Method;
import org.springframework.aop.MethodBeforeAdvice;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 */
public class BeforeAdviceDemo implements MethodBeforeAdvice {
    /**
     * Callback before a given method is invoked.
     * @param method method being invoked
     * @param args arguments to the method
     * @param target target of the method invocation. May be {@code null}.
     * @throws Throwable if this object wishes to abort the call.
     * Any exception thrown will be returned to the caller if it's
     * allowed by the method signature. Otherwise the exception
     * will be wrapped as a runtime exception.
     */
    @Override public void before(Method method, Object[] args, Object target) throws Throwable {
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
        System.out.println("I'am befored advisor with schema-based ...");
    }
}
