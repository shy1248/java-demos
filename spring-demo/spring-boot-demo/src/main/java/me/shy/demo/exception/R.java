/**
 * @Date        : 2020-10-27 23:43:08
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 响应实体类
 *
 * 定义响应结果类需要注意几个地方：
 * 1.响应结果实体类由于要进行网络传输，因此必须实现序列化接口；
 * 2.3个 fill 方法之所以要返回 this，是为了响应结果给客户端；
 * 3.该类使用的泛型 T，是因为响应体中可能需要装入不同的类型 pojo 实体类；
 *
 */
package me.shy.demo.exception;

import java.io.Serializable;

import lombok.Data;

@Data
public class R<T> implements Serializable {
    private static final long serialVersionUID = 4830807258567519475L;
    // 响应状态码
    private int respCode;
    // 响应状态信息
    private String msg;
    // 响应数据体
    private T data;

    public R<T> fillCode(int respCode, String msg) {
        this.respCode = respCode;
        this.msg = msg;
        return this;
    }

    public R<T> fillCode(ResponseStatusEnum responseStatus) {
        this.respCode = responseStatus.getCode();
        this.msg = responseStatus.getMessage();
        return this;
    }

    public R<T> fillData(T data) {
        this.respCode = ResponseStatusEnum.SUCCESS.getCode();
        this.msg = ResponseStatusEnum.SUCCESS.getMessage();
        this.data = data;
        return this;
    }
}
