/**
 * @Date        : 2020-10-27 23:59:58
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 需要捕获的异常类
 */
package me.shy.spring.exception;

import lombok.Data;

@Data
public class DefinedException extends RuntimeException{
    private static final long serialVersionUID = 6568428154226026810L;
    private int code;
    private String message;

    public DefinedException(ResponseStatusEnum responseStatus) {
        this.code = responseStatus.getCode();
        this.message = responseStatus.getMessage();
    }
}
