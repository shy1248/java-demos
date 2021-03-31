/**
 * @Since: 2019-12-07 20:19:47
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 * @LastTime: 2019-12-07 21:02:20
 */
package me.shy.spring.web.util;

import me.shy.spring.web.response.ResponseWarpper;

public class ResponseUtil {
    public static ResponseWarpper success(int code, Object object) {
        ResponseWarpper warpper = new ResponseWarpper();
        warpper.setCode(code);
        warpper.setMsg("Success");
        warpper.setData(object);
        return warpper;
    }

    public static ResponseWarpper success(int code) {
        ResponseWarpper warpper = new ResponseWarpper();
        warpper.setCode(code);
        warpper.setMsg("Success");
        return warpper;
    }

    public static ResponseWarpper failed(int code, String msg) {
        ResponseWarpper warpper = new ResponseWarpper();
        warpper.setCode(code);
        warpper.setMsg(msg);
        return warpper;
    }
}
