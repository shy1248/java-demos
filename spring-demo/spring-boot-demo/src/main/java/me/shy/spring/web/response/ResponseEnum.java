/**
 * @Since: 2019-12-07 19:33:32
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 * @LastTime: 2019-12-07 22:13:25
 */
package me.shy.spring.web.response;

public enum ResponseEnum {
    SECCESS(0, "SUCESS"), UNKNOW(-1, "UNKNOW"), PRIMARY_SCHOOL(100,
        "You may be go to the primary school"), MIDDLE_SCHOOL(101, "You may be go to the middle shcool");

    private int code;
    private String msg;

    private ResponseEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * @return the code
     */
    public int getCode() {
        return code;
    }

    /**
     * @return the msg
     */
    public String getMsg() {
        return msg;
    }
}
