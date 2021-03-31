/**
 * @Date        : 2020-10-28 00:03:54
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 全局异常捕获类
 */
package me.shy.spring.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
// 该注解表示开启全局的异常捕获
@RestControllerAdvice
public class GlobalExceptionHandler<T> {

    // 处理 EmptyResultException 的异常
    @ExceptionHandler(DefinedException.class)
    public R<T> definedExceptionHandle(DefinedException e) {
        log.error("已捕获异常: {}", e.getMessage());
        return new R<T>().fillCode(e.getCode(), e.getMessage());
    }

    // 其它未定义的异常处理
    @ExceptionHandler(Exception.class)
    public R<T> undefinedExceptionHandle(Exception e) {
        log.error("系统错误: {}", e.getMessage());
        return new R<T>().fillCode(ResponseStatusEnum.ERROR);
    }
}
