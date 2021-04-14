/**
 * @Date        : 2021-04-11 14:20:55
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : App 主行为日志
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
import me.shy.rt.dataware.datamocker.enums.ActionType;
import me.shy.rt.dataware.datamocker.enums.ItemType;
import me.shy.rt.dataware.datamocker.enums.Page;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppActionLog {
    /** 行为时间戳 */
    private Long timestamp;
    /** 行为类型 */
    private ActionType actionId;
    /** 行为操作对象类型 */
    private ItemType itemType;
    /** 行为操作对象 */
    private String item;
    /** 预留字段1 */
    private String extend1;
    /** 预留字段2 */
    private String extend2;

    public AppActionLog(ActionType actionId, ItemType itemType, String item) {
        this.actionId = actionId;
        this.itemType = itemType;
        this.item = item;
    }

    public static List<AppActionLog> batchInstances(AppPageLog appPage, Long timestamp, Integer duration) {
        AppActionLog action = null;
        List<AppActionLog> actions = new ArrayList<>();

        Boolean isFavorite = RandomWeightOption.<Boolean>builder().add(true, DataMockerConfig.favoriteRate)
                .add(false, 100 - DataMockerConfig.favoriteRate).build().nextPayload();
        Boolean isInCart = RandomWeightOption.<Boolean>builder().add(true, DataMockerConfig.inCartRate)
                .add(false, 100 - DataMockerConfig.inCartRate).build().nextPayload();
        Boolean isCartAddNum = RandomWeightOption.<Boolean>builder().add(true, DataMockerConfig.cartAddNumberRate)
                .add(false, 100 - DataMockerConfig.cartAddNumberRate).build().nextPayload();
        Boolean isCartMinusNum = RandomWeightOption.<Boolean>builder().add(true, DataMockerConfig.cartMinusNumberRate)
                .add(false, 100 - DataMockerConfig.cartMinusNumberRate).build().nextPayload();
        Boolean isCartRemove = RandomWeightOption.<Boolean>builder().add(true, DataMockerConfig.cartRemoveRate)
                .add(false, 100 - DataMockerConfig.cartRemoveRate).build().nextPayload();
        Boolean isGetCoupon = RandomWeightOption.<Boolean>builder().add(true, DataMockerConfig.userGetCouponRate)
                .add(false, 100 - DataMockerConfig.userGetCouponRate).build().nextPayload();
        Boolean isAddAddress = RandomWeightOption.<Boolean>builder().add(true, DataMockerConfig.newReceviedAddressRate)
                .add(false, 100 - DataMockerConfig.newReceviedAddressRate).build().nextPayload();
        Boolean isFavoriteCancel = RandomWeightOption.<Boolean>builder().add(true, DataMockerConfig.favoriteCancelRate)
                .add(false, 100 - DataMockerConfig.favoriteCancelRate).build().nextPayload();

        if (appPage.getPage() == Page.GOODS_DETAIL) {
            if (isFavorite) {
                action = new AppActionLog(ActionType.FAVORITE_ADD, appPage.getItemType(), appPage.getItem());
                actions.add(action);
            }
            if (isInCart) {
                action = new AppActionLog(ActionType.CART_ADD, appPage.getItemType(), appPage.getItem());
                actions.add(action);
            }
            if (isGetCoupon) {
                int couponId = RandomNumeric.nextInteger(1, DataMockerConfig.maxCouponId);
                action = new AppActionLog(ActionType.GET_COUPON, ItemType.COUPON_ID, String.valueOf(couponId));
                actions.add(action);
            }

        } else if (appPage.getPage() == Page.CART) {

            if (isCartAddNum) {
                String skuId = RandomNumeric.nextInteger(1, DataMockerConfig.maxSkuId) + "";
                action = new AppActionLog(ActionType.CART_ADD_NUM, ItemType.SKU_ID, skuId);
                actions.add(action);
            }
            if (isCartMinusNum) {
                String skuId = RandomNumeric.nextInteger(1, DataMockerConfig.maxSkuId) + "";
                action = new AppActionLog(ActionType.CART_MINUS_NUM, ItemType.SKU_ID, skuId);
                actions.add(action);
            }
            if (isCartRemove) {
                String skuId = RandomNumeric.nextInteger(1, DataMockerConfig.maxSkuId) + "";
                action = new AppActionLog(ActionType.CART_REMOVE, ItemType.SKU_ID, skuId);
                actions.add(action);
            }

        } else if (appPage.getPage() == Page.TRADE) {
            if (isAddAddress) {
                action = new AppActionLog(ActionType.TRADE_ADD_ADDRESS, null, null);
                actions.add(action);
            }

        } else if (appPage.getPage() == Page.FAVORITE) {
            int skuId = RandomNumeric.nextInteger(1, DataMockerConfig.maxSkuId);
            for (int i = 0; i < 3; i++) {
                if (isFavoriteCancel) {
                    action = new AppActionLog(ActionType.FAVORITE_CANEL, ItemType.SKU_ID, skuId + i + "");
                    actions.add(action);
                }
            }
        }

        int size = actions.size();
        long avgActionTime = duration / (size + 1);
        for (int i = 1; i <= size; i++) {
            actions.get(i - 1).setTimestamp(timestamp + i * avgActionTime);
        }
        return actions;
    }
}
