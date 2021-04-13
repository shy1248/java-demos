/**
 * @Date        : 2021-04-11 14:19:41
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : App client error.
 */
package me.shy.rt.dataware.demo.datamocker.actionlogmocker.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.shy.rt.dataware.demo.datamocker.common.RandomNumeric;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppError {
    private int errorCode;
    private String message;

    public static AppError newInstance() {
        return new AppError(RandomNumeric.nextInteger(1001, 4001),
                "Exception in thread \\  java.net.SocketTimeoutException\\n \\tat me.shy.realtime.dataware.malldatamocker.logmocker.bean.AppError.main(AppError.java: A mocker exception");
    }
}
