package me.shy.demo.aop.advice;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 */
public class AroundAdviceDemo implements MethodInterceptor {
    /**
     * Implement this method to perform extra treatments before and
     * after the invocation. Polite implementations would certainly
     * like to invoke {@link Joinpoint#proceed()}.
     * @param invocation the method invocation joinpoint
     * @return the result of the call to {@link Joinpoint#proceed()};
     * might be intercepted by the interceptor
     * @throws Throwable if the interceptors or the target object
     * throws an exception
     */
    @Override public Object invoke(MethodInvocation invocation) throws Throwable {
        // 前置逻辑
        System.out.println("This message is attached by: " + this.getClass().getName() + " at before.[Schema-based]");
        // 调用切点方法
        Object returnValue = invocation.proceed();
        // 后置逻辑
        System.out.println("This message is attached by: " + this.getClass().getName() + " at after.[Schema-based]");
        // 返回值
        return returnValue;
    }
}
