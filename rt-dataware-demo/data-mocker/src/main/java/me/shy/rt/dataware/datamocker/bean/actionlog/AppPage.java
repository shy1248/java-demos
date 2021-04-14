/**
 * @Date        : 2021-04-11 12:23:06
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : App 页面日志
 */

package me.shy.rt.dataware.datamocker.bean.actionlog;

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
        AppPage s = new AppPage();
        s.lastPageId = lastPageId;
        s.duration = duration;

        if (pageId == PageId.GOODS_LIST) {
            s.itemType = ItemType.KEYWORD;
            s.item = new RandomWeightOption<String>(DataMockerConfig.searchKeywords).nextPayload();
        } else if (pageId == PageId.TRADE || pageId == PageId.PAYMENT || pageId == PageId.PAYMENT_DONE) {
            s.itemType = ItemType.SKU_IDS;
            s.item = RandomNumeric.nextString(1, DataMockerConfig.maxSkuId, RandomNumeric.nextInteger(1, 3), ",",
                    false);
        } else if (pageId == PageId.GOODS_DETAIL || pageId == PageId.GOODS_SPEC || pageId == PageId.COMMENT
                || pageId == PageId.COMMENT_LIST) {
            Integer[] sourceTypeRates = DataMockerConfig.skuDetailSourceTypeRates;
            RandomWeightOption<DisplayType> sourceTypeOptionGroup = RandomWeightOption.<DisplayType>builder()
                    .add(DisplayType.QUERY, sourceTypeRates[0]).add(DisplayType.PROMOTION, sourceTypeRates[1])
                    .add(DisplayType.RECOMMEND, sourceTypeRates[2]).add(DisplayType.ACTIVITY, sourceTypeRates[3])
                    .build();
            s.sourceType = sourceTypeOptionGroup.nextPayload();
            s.itemType = ItemType.SKU_ID;
            s.item = RandomNumeric.nextInteger(0, DataMockerConfig.maxSkuId) + "";
        }

        return s;
    }
}
