/**
 * @Date        : 2021-02-12 21:49:53
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : Baby 事件封装
 */
package me.shy.dp.observer;

public class BabyEvent extends Event<Baby> {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
