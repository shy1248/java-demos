/**
 * @Date        : 2021-04-11 14:19:30
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : App 通用日志
 */

package me.shy.rt.dataware.datamocker.bean.actionlog;

import lombok.Data;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import me.shy.rt.dataware.datamocker.config.DataMockerConfig;
import me.shy.rt.dataware.datamocker.util.RandomNumeric;
import me.shy.rt.dataware.datamocker.util.RandomWeightOption;
import me.shy.rt.dataware.datamocker.util.WeightOption;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppCommonLog {
    /** 设备 id */
    private String deviceId;
    /** 用户 id */
    private String userId;
    /** 程序版本号 */
    private String version;
    /** 渠道号 */
    private String channel;
    /** 系统版本 */
    private String os;
    /** 区域 */
    private String area;
    /** 设备型号 */
    private String deviceType;
    /** 设备品牌 */
    private String product;
    /** 是否新用户 */
    private String isNew;

    public static AppCommonLog newInstance(DataMockerConfig c) {
        AppCommonLog s = new AppCommonLog();

        s.deviceId = "mid_" + RandomNumeric.nextInteger(1, c.maxDeviceId);
        s.area = new RandomWeightOption<String>(new WeightOption<String>("110000", 30),
                new WeightOption<String>("310000", 20), new WeightOption<String>("230000", 10),
                new WeightOption<String>("370000", 10), new WeightOption<String>("420000", 5),
                new WeightOption<String>("440000", 20), new WeightOption<String>("500000", 5),
                new WeightOption<String>("530000", 5)).nextPayload();
        s.deviceType = new RandomWeightOption<String>(new WeightOption<String>("Xiaomi 9", 30),
                new WeightOption<String>("Xiaomi 10 Pro ", 30), new WeightOption<String>("Xiaomi Mix2 ", 30),
                new WeightOption<String>("iPhone X", 20), new WeightOption<String>("iPhone 8", 20),
                new WeightOption<String>("iPhone Xs", 20), new WeightOption<String>("iPhone Xs Max", 20),
                new WeightOption<String>("Huawei P30", 10), new WeightOption<String>("Huawei Mate 30", 10),
                new WeightOption<String>("Redmi k30", 10), new WeightOption<String>("Honor 20s", 5),
                new WeightOption<String>("vivo iqoo3", 20), new WeightOption<String>("Oneplus 7", 5),
                new WeightOption<String>("Sumsung Galaxy S20", 3)).nextPayload();
        s.product = s.deviceType.split(" ")[0];
        if (s.product.equals("iPhone")) {
            s.channel = "Appstore";
            s.os = "iOS " + new RandomWeightOption<String>(new WeightOption<String>("13.3.1", 30),
                    new WeightOption<String>("13.2.9", 10), new WeightOption<String>("13.2.3", 10),
                    new WeightOption<String>("12.4.1", 5)).nextPayload();
        } else {
            s.channel = new RandomWeightOption<String>(new WeightOption<String>("xiaomi", 30),
                    new WeightOption<String>("wandoujia", 10), new WeightOption<String>("web", 10),
                    new WeightOption<String>("huawei", 5), new WeightOption<String>("oppo", 20),
                    new WeightOption<String>("vivo", 5), new WeightOption<String>("360", 5)).nextPayload();
            s.os = "Android " + new RandomWeightOption<String>(new WeightOption<String>("11.0", 70),
                    new WeightOption<String>("10.0", 20), new WeightOption<String>("9.0", 5),
                    new WeightOption<String>("8.1", 5)).nextPayload();
        }
        s.version = "v" + new RandomWeightOption<String>(new WeightOption<String>("2.1.134", 70),
                new WeightOption<String>("2.1.132", 20), new WeightOption<String>("2.1.111", 5),
                new WeightOption<String>("2.0.1", 5)).nextPayload();
        s.userId = RandomNumeric.nextInteger(1, c.maxUserId) + "";
        s.isNew = RandomNumeric.nextInteger(0, 1) + "";
        return s;
    }
}
