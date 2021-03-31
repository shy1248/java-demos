package me.shy.spring.aop.advice;

import java.lang.reflect.Method;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.stereotype.Component;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 */
@Component public class AfterAdviceDemo implements AfterReturningAdvice {
    /**
     * Callback after a given method successfully returned.
     * @param returnValue the value returned by the method, if any
     * @param method method being invoked
     * @param args arguments to the method
     * @param target target of the method invocation. May be {@code null}.
     * @throws Throwable if this object wishes to abort the call.
     * Any exception thrown will be returned to the caller if it's
     * allowed by the method signature. Otherwise the exception
     * will be wrapped as a runtime exception.
     */
    @Override public void afterReturning(Object returnValue, Method method, Object[] args, Object target)
        throws Throwable {
        System.out.println("I'm after advisor with schema-based ...");
        System.out.println("Pointcut method returned: " + returnValue);
    }
}
