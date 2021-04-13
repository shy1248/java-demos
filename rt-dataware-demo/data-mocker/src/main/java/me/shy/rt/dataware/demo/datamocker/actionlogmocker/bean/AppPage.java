/**
 * @Date        : 2021-04-11 12:23:06
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : App client pages.
 */
package me.shy.rt.dataware.demo.datamocker.actionlogmocker.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.shy.rt.dataware.demo.datamocker.common.RandomNumeric;
import me.shy.rt.dataware.demo.datamocker.common.RandomWeightOption;
import me.shy.rt.dataware.demo.datamocker.actionlogmocker.config.AppConfig;
import me.shy.rt.dataware.demo.datamocker.actionlogmocker.enums.DisplayType;
import me.shy.rt.dataware.demo.datamocker.actionlogmocker.enums.ItemType;
import me.shy.rt.dataware.demo.datamocker.actionlogmocker.enums.PageId;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppPage {
    private PageId lastPageId;
    private PageId pageId;
    private ItemType itemType;
    private String item;
    private Integer duration;
    private String extend1;
    private String extend2;
    private DisplayType sourceType;

    public static AppPage newInstance(PageId pageId, PageId lastPageId, Integer duration) {
        AppPage instance = new AppPage();
        instance.lastPageId = lastPageId;
        instance.duration = duration;

        if (pageId == PageId.GOODS_LIST) {
            instance.itemType = ItemType.KEYWORD;
            instance.item = new RandomWeightOption<String>(AppConfig.SEARCH_KEYWORDS).nextPayload();
        } else if (pageId == PageId.TRADE || pageId == PageId.PAYMENT || pageId == PageId.PAYMENT_DONE) {
            instance.itemType = ItemType.SKU_IDS;
            instance.item = RandomNumeric.nextString(1, AppConfig.MAX_SKU_ID, RandomNumeric.nextInteger(1, 3), ",",
                    false);
        } else if (pageId == PageId.GOODS_DETAIL || pageId == PageId.GOODS_SPEC || pageId == PageId.COMMENT
                || pageId == PageId.COMMENT_LIST) {
            RandomWeightOption<DisplayType> sourceTypeOptionGroup = RandomWeightOption.<DisplayType>builder()
                    .add(DisplayType.QUERY, AppConfig.SOURCE_TYPE_RATES[0])
                    .add(DisplayType.PROMOTION, AppConfig.SOURCE_TYPE_RATES[1])
                    .add(DisplayType.RECOMMEND, AppConfig.SOURCE_TYPE_RATES[2])
                    .add(DisplayType.ACTIVITY, AppConfig.SOURCE_TYPE_RATES[3]).build();
            instance.sourceType = sourceTypeOptionGroup.nextPayload();
            instance.itemType = ItemType.SKU_ID;
            instance.item = RandomNumeric.nextInteger(0, AppConfig.MAX_SKU_ID) + "";
        }

        return instance;
    }
}
