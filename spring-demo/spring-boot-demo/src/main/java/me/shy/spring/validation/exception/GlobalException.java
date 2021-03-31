/**
 * @Date        : 2020-10-25 00:52:39
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : -
 */
package me.shy.spring.validation.exception;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class GlobalException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private int errorCode;
    private String msg;

    public GlobalException(int errorCode, String msg) {
        super(msg);
        this.errorCode = errorCode;
        this.msg = msg;
    }

}
