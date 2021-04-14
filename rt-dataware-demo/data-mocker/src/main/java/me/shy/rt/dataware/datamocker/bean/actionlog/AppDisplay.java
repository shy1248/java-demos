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
import me.shy.rt.dataware.datamocker.enums.PageId;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppDisplay {
    private ItemType itemType;
    private String item;
    private DisplayType displayType;
    private Integer order;
    private Integer positionId;

    public static List<AppDisplay> batchInstances(AppPage appPage) {
        List<AppDisplay> displays = new ArrayList<>();
        if (appPage.getPageId() == PageId.HOME || appPage.getPageId() == PageId.DISCOVERY
                || appPage.getPageId() == PageId.CATEGORY) {
            int displayCount = RandomNumeric.nextInteger(1, DataMockerConfig.maxDisplayCount);
            int positionId = RandomNumeric.nextInteger(1, DataMockerConfig.maxPostionId);
            for (int i = 0; i < displayCount; i++) {
                String activityId = RandomNumeric.nextInteger(1, DataMockerConfig.maxActivityCount) + "";
                AppDisplay appDisplay = new AppDisplay(ItemType.ACTIVITY_ID, activityId, DisplayType.ACTIVITY, i, positionId);
                displays.add(appDisplay);
            }
        }
        if (appPage.getPageId() == PageId.HOME // 首页
                || appPage.getPageId() == PageId.DISCOVERY // 发现
                || appPage.getPageId() == PageId.CATEGORY // 分类
                || appPage.getPageId() == PageId.ACTIVITY // 活动
                || appPage.getPageId() == PageId.GOODS_DETAIL // 商品明细
                || appPage.getPageId() == PageId.GOODS_SPEC // 商品规格
                || appPage.getPageId() == PageId.GOODS_LIST // 商品列表
        ) {
            int displayCount = RandomNumeric.nextInteger(DataMockerConfig.minDisplayCount,DataMockerConfig.maxDisplayCount);
            int activityCount = displays.size(); // 商品显示从活动后面开始
            for (int i = 0; i < displayCount + activityCount; i++) {
                String skuId = RandomNumeric.nextInteger(1, DataMockerConfig.maxSkuId) + "";
                int positionId = RandomNumeric.nextInteger(1, DataMockerConfig.maxPostionId);
                DisplayType displayType = RandomWeightOption.<DisplayType>builder().add(DisplayType.PROMOTION, 30)
                        .add(DisplayType.QUERY, 60).add(DisplayType.RECOMMEND, 20).build().nextPayload();
                AppDisplay appDisplay = new AppDisplay(ItemType.SKU_ID, skuId, displayType, i, positionId);
                displays.add(appDisplay);
            }
        }
        return displays;
    }
}
