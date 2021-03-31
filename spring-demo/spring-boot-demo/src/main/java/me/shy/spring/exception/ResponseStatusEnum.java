/**
 * @Date        : 2020-10-27 23:35:35
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 响应状态枚举
 */
package me.shy.spring.exception;

import lombok.Getter;

@Getter
public enum ResponseStatusEnum {
    SUCCESS(0, "请求成功"),
    ERROR(500, "未知错误"),
    ERROR_EMPTY_RESULT(1001, "查询结果为空"),
    ERROR_INCOMPLETE_REQUEST(1002, "请求参数不完整");

    private int code;
    private String message;

    private ResponseStatusEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
