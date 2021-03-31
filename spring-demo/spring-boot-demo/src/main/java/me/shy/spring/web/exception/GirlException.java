/**
 * @Since: 2019-12-07 20:46:38
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 * @LastTime: 2019-12-07 22:21:10
 */
package me.shy.spring.web.exception;

import me.shy.spring.web.response.ResponseEnum;

public class GirlException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private int code;

    public GirlException(ResponseEnum responseEnum) {
        super(responseEnum.getMsg());
        this.code = responseEnum.getCode();
    }

    /**
     * @return the code
     */
    public int getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(int code) {
        this.code = code;
    }
}
