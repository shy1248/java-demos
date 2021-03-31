/**
 * @Date        : 2020-10-25 00:59:46
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 全局异常拦截
 */
package me.shy.spring.validation.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionAdvicer {

    @ExceptionHandler(Exception.class)
    public Object exceptionHandle(Exception e) {
        // 打印堆栈
        e.printStackTrace();
        if (e instanceof GlobalException) {
            GlobalException ge = (GlobalException) e;
            return ge.getMsg();
        }
        return "系统异常";
    }
}
