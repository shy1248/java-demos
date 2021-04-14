/**
 * @Date        : 2021-04-11 14:20:47
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : App 曝光日志
 */

package me.shy.rt.dataware.datamocker.bean.actionlog;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import me.shy.rt.dataware.datamocker.util.RandomNumeric;
import me.shy.rt.dataware.datamocker.util.RandomWeightOption;
import me.shy.rt.dataware.datamocker.config.DataMockerConfig;
import me.shy.rt.dataware.datamocker.enums.DisplayType;
import me.shy.rt.dataware.datamocker.enums.ItemType;
import me.shy.rt.dataware.datamocker.enums.Page;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppDisplayLog {
    /** 操作行为对象类型 */
    private ItemType itemType;
    /** 操作行为对象 */
    private String item;
    /** 曝光类型 */
    private DisplayType displayType;
    /** 订单 */
    private Integer order;
    /** 位置 id */
    private Integer positionId;

    public static List<AppDisplayLog> batchInstances(AppPageLog appPage) {
        List<AppDisplayLog> displayLogs = new ArrayList<>();
        if (appPage.getPage() == Page.HOME || appPage.getPage() == Page.DISCOVERY
                || appPage.getPage() == Page.CATEGORY) {
            int displayCount = RandomNumeric.nextInteger(1, DataMockerConfig.maxDisplayCount);
            int positionId = RandomNumeric.nextInteger(1, DataMockerConfig.maxPostionId);
            for (int i = 0; i < displayCount; i++) {
                String activityId = RandomNumeric.nextInteger(1, DataMockerConfig.maxActivityCount) + "";
                AppDisplayLog appDisplay = new AppDisplayLog(ItemType.ACTIVITY_ID, activityId, DisplayType.ACTIVITY, i, positionId);
                displayLogs.add(appDisplay);
            }
        }
        if (appPage.getPage() == Page.HOME // 首页
                || appPage.getPage() == Page.DISCOVERY // 发现
                || appPage.getPage() == Page.CATEGORY // 分类
                || appPage.getPage() == Page.ACTIVITY // 活动
                || appPage.getPage() == Page.GOODS_DETAIL // 商品明细
                || appPage.getPage() == Page.GOODS_SPEC // 商品规格
                || appPage.getPage() == Page.GOODS_LIST // 商品列表
        ) {
            int displayCount = RandomNumeric.nextInteger(DataMockerConfig.minDisplayCount,DataMockerConfig.maxDisplayCount);
            int activityCount = displayLogs.size(); // 商品显示从活动后面开始
            for (int i = 0; i < displayCount + activityCount; i++) {
                String skuId = RandomNumeric.nextInteger(1, DataMockerConfig.maxSkuId) + "";
                int positionId = RandomNumeric.nextInteger(1, DataMockerConfig.maxPostionId);
                DisplayType displayType = RandomWeightOption.<DisplayType>builder().add(DisplayType.PROMOTION, 30)
                        .add(DisplayType.QUERY, 60).add(DisplayType.RECOMMEND, 20).build().nextPayload();
                AppDisplayLog appDisplay = new AppDisplayLog(ItemType.SKU_ID, skuId, displayType, i, positionId);
                displayLogs.add(appDisplay);
            }
        }
        return displayLogs;
    }
}
