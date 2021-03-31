/**
 * @Since: 2019-12-07 20:52:42
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 * @LastTime: 2019-12-07 22:23:07
 */
package me.shy.spring.web.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import me.shy.spring.web.response.ResponseEnum;
import me.shy.spring.web.response.ResponseWarpper;
import me.shy.spring.web.util.ResponseUtil;

@ControllerAdvice @ResponseBody public class GirlExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GirlException.class);

    @ExceptionHandler(value = {Exception.class})

    public ResponseWarpper handle(Exception e) {
        if (e instanceof GirlException) {
            GirlException exception = (GirlException)e;
            return ResponseUtil.failed(exception.getCode(), exception.getMessage());
        } else {
            logger.error("{}:", ResponseEnum.UNKNOW.getMsg(), e);
            return ResponseUtil.failed(ResponseEnum.UNKNOW.getCode(), ResponseEnum.UNKNOW.getMsg());
        }
    }
}
