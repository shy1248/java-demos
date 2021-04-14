/**
 * @Date        : 2021-04-11 14:18:52
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : App 上报日志
 */

package me.shy.rt.dataware.datamocker.bean.actionlog;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import com.google.gson.Gson;

import me.shy.rt.dataware.datamocker.config.DataMockerConfig;
import me.shy.rt.dataware.datamocker.util.RandomWeightOption;

@Data
@Builder(builderClassName = "AppLogBuilder")
public class AppLog {
    /** 日志产生时的时间戳 */
    private Long timestamp;
    /** 通用信息日志 */
    private AppCommonLog common;
    /** 页面访问日志 */
    private AppPageLog page;
    /** 错误日志 */
    private AppErrorLog error;
    /** 通知日志 */
    private AppNoticeLog notice;
    /** 启动日志 */
    private AppStartLog start;
    /** 本次所有商品曝光日志 */
    private List<AppDisplayLog> displayLogs;
    /** 本次所有行为日志 */
    private List<AppActionLog> actionLogs;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public static class AppLogBuilder {
        public void nextError() {
            boolean isError = RandomWeightOption.<Boolean>builder().add(true, DataMockerConfig.errorRate)
                    .add(false, 100 - DataMockerConfig.errorRate).build().nextPayload();
            if (isError) {
                this.error = AppErrorLog.newInstance();
            }
        }
    }
}
