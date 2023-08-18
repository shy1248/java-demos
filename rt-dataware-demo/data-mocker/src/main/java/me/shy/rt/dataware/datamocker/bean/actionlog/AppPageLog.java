/**
 * @Date        : 2021-04-11 12:23:06
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : App 页面访问日志
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
import me.shy.rt.dataware.datamocker.enums.Page;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppPageLog {
    /** 上一次的访问页面 */
    private Page lastPage;
    /** 当前正在访问的页面 */
    private Page page;
    /** 行为操作对象类型 */
    private ItemType itemType;
    /** 行为操作对象 */
    private String item;
    /** 页面停留时长 */
    private Integer duration;
    /** 商品曝光源类型 */
    private DisplayType sourceType;
    /** 预留字段1 */
    private String extend1;
    /** 预留字段2 */
    private String extend2;

    public static AppPageLog newInstance(DataMockerConfig c, Page page, Page lastPage, Integer duration) {
        AppPageLog s = new AppPageLog();
        s.page = page;
        s.lastPage = lastPage;
        s.duration = duration;

        if (page == Page.GOODS_LIST) {
            s.itemType = ItemType.KEYWORD;
            s.item = new RandomWeightOption<String>(c.searchKeywords).nextPayload();
        } else if (page == Page.TRADE || page == Page.PAYMENT || page == Page.PAYMENT_DONE) {
            s.itemType = ItemType.SKU_IDS;
            s.item = RandomNumeric.nextString(1, c.maxSkuId, RandomNumeric.nextInteger(1, 3), ",",
                    false);
        } else if (page == Page.GOODS_DETAIL || page == Page.GOODS_SPEC || page == Page.COMMENT
                || page == Page.COMMENT_LIST) {
            Integer[] sourceTypeRates = c.skuDetailSourceTypeRates;
            RandomWeightOption<DisplayType> sourceTypeOptionGroup = RandomWeightOption.<DisplayType>builder()
                    .add(DisplayType.QUERY, sourceTypeRates[0]).add(DisplayType.PROMOTION, sourceTypeRates[1])
                    .add(DisplayType.RECOMMEND, sourceTypeRates[2]).add(DisplayType.ACTIVITY, sourceTypeRates[3])
                    .build();
            s.sourceType = sourceTypeOptionGroup.nextPayload();
            s.itemType = ItemType.SKU_ID;
            s.item = RandomNumeric.nextInteger(0, c.maxSkuId) + "";
        }

        return s;
    }
}
