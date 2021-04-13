/**
 * @Date        : 2021-04-11 11:50:16
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : App client start log.
 */
package me.shy.rt.dataware.demo.datamocker.actionlogmocker.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.shy.rt.dataware.demo.datamocker.common.RandomNumeric;
import me.shy.rt.dataware.demo.datamocker.common.RandomWeightOption;
import me.shy.rt.dataware.demo.datamocker.common.WeightOption;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppStart {
    // 开启 App 方式，安装后进入：install，点击图标：icon，点击通知：notice
    private String startTypeEntry;
    // 开屏广告 ID
    private Long openScreenAdId;
    // 开屏广告时长，毫秒
    private Integer openScreenAdTime;
    // 开屏广告持续多长时间后用户点击跳过，未点击为 0
    private Integer openScreenAdSkipped;
    // 加载时长：计算下拉开始到接口返回数据的时间，（开始加载报0，加载成功或加载失败才上报时间）
    private Integer loadingTime;

    public static AppStart newInstance() {
        AppStart instance = new AppStart();
        instance.startTypeEntry = new RandomWeightOption<String>(new WeightOption<String>("install", 5),
                new WeightOption<String>("icon", 70), new WeightOption<String>("notice", 20)).nextPayload();
        instance.openScreenAdId = RandomNumeric.nextInteger(0, 20) + 0L;
        instance.openScreenAdTime = RandomNumeric.nextInteger(1000, 5000);
        instance.openScreenAdSkipped = RandomWeightOption.<Integer>builder().add(0, 50)
                .add(RandomNumeric.nextInteger(1000, instance.openScreenAdTime), 50).build().nextPayload();
        instance.loadingTime = RandomNumeric.nextInteger(1000, 20000);
        return instance;
    }
}
