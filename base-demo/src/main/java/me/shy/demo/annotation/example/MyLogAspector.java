/**
 * @Date        : 2020-10-18 22:28:08
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 自定义切面
 */
package me.shy.demo.annotation.example;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

// 1.增加 @Aspect 注解，表明该类是一个切面
@Aspect
@Component
public class MyLogAspector {

    // 2.定义切点，logPointCut 为切点名称
    // @PointCut 注解表示该方法是一个切点
    // @annotation 注解表示该切点切到一个注解上，后边带上改目标注解的全限定名
    @Pointcut("@annotation(me.shy.demo.annotation.example.MyLog)")
    public void logPointCut() {
    }

    // 3.环绕通知
    // 该方法的参数 ProceedingJoinPoint 即代表了获取到的切点方法
    @Around("logPointCut()")
    public Object logAround(ProceedingJoinPoint joinPoint) {
        // 获取使用了 @MyLog 注解（即上边定义的切点）的方法名称
        String method = joinPoint.getSignature().getName();
        // 获取使用了 @MyLog 注解（即上边定义的切点）的方法参数列表
        Object[] args = joinPoint.getArgs();
        // 执行方法前增加日志
        System.out.println(String.format("Entering method [%s], and args is %s ...", method, Arrays.asList(args)));
        // 执行切点方法本身，proceed 用来接受切点方法的返回值
        Object proceed = null;
        try {
            proceed = joinPoint.proceed();
        } catch (Throwable t) {
            // 执行切点方法抛异常时
            System.err.println(String.format("An error occourd: %s", t.getMessage()));
        }
        // 执行完切点方法
        System.out.println("Leaved method.");
        // 返回切点方法执行结果
        return proceed;
    }
}
