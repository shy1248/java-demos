/**
 * @Since: 2019-12-07 19:35:22
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 * @LastTime: 2019-12-07 20:13:39
 */
package me.shy.spring.web.aspect;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect @Component public class HttpAspect {

    private static final Logger logger = LoggerFactory.getLogger(HttpAspect.class);

    @Pointcut("execution(public * me.shy.demo.boot.web.controller.GirlController.*(..))") public void log() {

    }

    @Before("log()") public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes requestAttributes =
            (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        // url
        StringBuffer url = request.getRequestURL();
        // method
        String requestMethod = request.getMethod();
        // ip
        String remoteAddr = request.getRemoteAddr();
        // handler
        String habdler = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        // params
        Object[] args = joinPoint.getArgs();
        logger.info("URL={}, RequestMethod={}, IP={}, Handler={}, Args={}", url, requestMethod, remoteAddr, habdler,
            args);
    }

    @After("log()") public void doAfter() {
        logger.info("Response returned!");
    }

    @AfterReturning(returning = "object", pointcut = "log()") public void doAfterReturning(Object object) {
        logger.info("Response={}", object.toString());
    }
}
