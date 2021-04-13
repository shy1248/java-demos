/**
 * @Date        : 2021-04-11 14:18:52
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : App client main.
 */
package me.shy.rt.dataware.demo.datamocker.actionlogmocker.bean;

import java.util.List;

import com.google.gson.Gson;

import lombok.Builder;
import lombok.Data;
import me.shy.rt.dataware.demo.datamocker.common.RandomWeightOption;
import me.shy.rt.dataware.demo.datamocker.actionlogmocker.config.AppConfig;

@Data
@Builder
public class AppMain {
    // 客户端日志产生时的时间
    private Long timestamp;
    private AppCommon common;
    private AppPage page;
    private AppError error;
    private AppNotice notice;
    private AppStart start;
    private List<AppDisplay> displays;
    private List<AppAction> actions;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public static class AppMainBuilder {
        public void nextError() {
            int errorRate = AppConfig.ERROR_RATE;
            boolean isError = RandomWeightOption.<Boolean>builder().add(true, errorRate).add(false, 100 - errorRate)
                    .build().nextPayload();
            if (isError) {
                this.error = AppError.newInstance();
            }
        }
    }
}
