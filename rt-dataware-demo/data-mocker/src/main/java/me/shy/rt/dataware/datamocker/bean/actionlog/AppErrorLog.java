/**
 * @Date        : 2021-04-11 14:19:41
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : App 错误日志.
 */

package me.shy.rt.dataware.datamocker.bean.actionlog;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import me.shy.rt.dataware.datamocker.util.RandomNumeric;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppErrorLog {
    /** 错误状态码 */
    private int errorCode;
    /** 错误信息 */
    private String message;

    public static AppErrorLog newInstance() {
        return new AppErrorLog(RandomNumeric.nextInteger(1001, 4001),
                "Exception in thread \\  java.net.SocketTimeoutException\\n \\tat me.shy.realtime.dataware.malldatamocker.logmocker.bean.AppError.main(AppError.java: A mocker exception");
    }
}
