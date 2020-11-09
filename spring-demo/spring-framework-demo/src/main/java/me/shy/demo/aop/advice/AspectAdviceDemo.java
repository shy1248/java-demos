package me.shy.demo.aop.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 */
@Component @Aspect public class AspectAdviceDemo {
    @Before(value = "execution(* me.shy.demo.framework.aop.AOPDemo.demo(int, String)) && args(p1,p2)", argNames = "p1, p2")
    public void atBefored(int p1, String p2) {
        System.out.println("This message is attached by: " + this.getClass().getName() + "at bedore.[By AspectJ]");
        System.out.println("Pointcut's arguments is: [" + p1 + ", " + p2 + "]");
    }

    @After("execution(* me.shy.demo.framework.aop.AOPDemo.demo(..))") public void atAftered() {
        System.out.println("This message is attached by: " + this.getClass().getName() + "at after.[By AspectJ]");
    }

    @Around("execution(boolean me.shy.demo.framework.aop.AOPDemo.demo(int, String))")
    public Object atAround(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println(
            "This message is attached by: " + this.getClass().getName() + "at araound-before.[By " + "AspectJ]");
        Object returnValue = joinPoint.proceed();
        System.out.println(
            "This message is attached by: " + this.getClass().getName() + "at araound-after.[By " + "AspectJ]");
        return returnValue;

    }

    @AfterThrowing("execution(* me.shy.demo.framework.aop.AOPDemo.demo(..))") public void atThrowed() {
        System.out.println("This message is attached by: " + this.getClass().getName() + "at throws.[By AspectJ]");
    }

    @AfterReturning("execution(* me.shy.demo.framework.aop.AOPDemo.demo(..))") public void atReturning() {
        System.out.println("This message is attached by: " + this.getClass().getName() + "at returning.[By AspectJ]");
    }
}
