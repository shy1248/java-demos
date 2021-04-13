/**
 * @Date        : 2021-04-11 14:20:55
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : App client actions.
 */

package me.shy.rt.dataware.demo.datamocker.actionlogmocker.bean;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.shy.rt.dataware.demo.datamocker.common.RandomNumeric;
import me.shy.rt.dataware.demo.datamocker.common.RandomWeightOption;
import me.shy.rt.dataware.demo.datamocker.actionlogmocker.enums.ActionId;
import me.shy.rt.dataware.demo.datamocker.actionlogmocker.enums.ItemType;
import me.shy.rt.dataware.demo.datamocker.actionlogmocker.enums.PageId;

import static me.shy.rt.dataware.demo.datamocker.actionlogmocker.config.AppConfig.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppAction {
    private Long timestamp;
    private ActionId actionId;
    private ItemType itemType;
    private String item;
    private String extend1;
    private String extend2;

    public AppAction(ActionId actionId, ItemType itemType, String item) {
        this.actionId = actionId;
        this.itemType = itemType;
        this.item = item;

    }

    public static List<AppAction> batchInstances(AppPage appPage, Long timestamp, Integer duration) {
        AppAction action = null;
        List<AppAction> actions = new ArrayList<>();

        Boolean isFavorite = RandomWeightOption.<Boolean>builder().add(true, FAVORITE_RATE)
                .add(false, 100 - FAVORITE_RATE).build().nextPayload();
        Boolean isInCart = RandomWeightOption.<Boolean>builder().add(true, CART_RATE).add(false, 100 - CART_RATE).build()
                .nextPayload();
        Boolean isCartAddNum = RandomWeightOption.<Boolean>builder().add(true, CART_ADD_NUM_RATE)
                .add(false, 100 - CART_ADD_NUM_RATE).build().nextPayload();
        Boolean isCartMinusNum = RandomWeightOption.<Boolean>builder().add(true, CART_MINUS_NUM_RATE)
                .add(false, 100 - CART_MINUS_NUM_RATE).build().nextPayload();
        Boolean isCartRemove = RandomWeightOption.<Boolean>builder().add(true, CART_REMOVE_RATE)
                .add(false, 100 - CART_REMOVE_RATE).build().nextPayload();
        Boolean isGetCoupon = RandomWeightOption.<Boolean>builder().add(true, GET_COUPON_RATE)
                .add(false, 100 - GET_COUPON_RATE).build().nextPayload();
        Boolean isAddAddress = RandomWeightOption.<Boolean>builder().add(true, ADD_ADDRESS_RATE)
                .add(false, 100 - ADD_ADDRESS_RATE).build().nextPayload();
        Boolean isFavoriteCancel = RandomWeightOption.<Boolean>builder().add(true, FAVORITE_CANCEL_RATE)
                .add(false, 100 - FAVORITE_CANCEL_RATE).build().nextPayload();

        if (appPage.getPageId() == PageId.GOODS_DETAIL) {
            if (isFavorite) {
                action = new AppAction(ActionId.FAVORITE_ADD, appPage.getItemType(), appPage.getItem());
                actions.add(action);
            }
            if (isInCart) {
                action = new AppAction(ActionId.CART_ADD, appPage.getItemType(), appPage.getItem());
                actions.add(action);
            }
            if (isGetCoupon) {
                int couponId = RandomNumeric.nextInteger(1, MAX_COUPON_ID);
                action = new AppAction(ActionId.GET_COUPON, ItemType.COUPON_ID, String.valueOf(couponId));
                actions.add(action);
            }

        } else if (appPage.getPageId() == PageId.CART) {
            if (isCartAddNum) {
                String skuId = RandomNumeric.nextInteger(1, MAX_SKU_ID) + "";
                action = new AppAction(ActionId.CART_ADD_NUM, ItemType.SKU_ID, skuId);
                actions.add(action);
            }
            if (isCartMinusNum) {
                String skuId = RandomNumeric.nextInteger(1, MAX_SKU_ID) + "";
                action = new AppAction(ActionId.CART_MINUS_NUM, ItemType.SKU_ID, skuId);
                actions.add(action);
            }
            if (isCartRemove) {
                String skuId = RandomNumeric.nextInteger(1, MAX_SKU_ID) + "";
                action = new AppAction(ActionId.CART_REMOVE, ItemType.SKU_ID, skuId);
                actions.add(action);
            }

        } else if (appPage.getPageId() == PageId.TRADE) {
            if (isAddAddress) {
                action = new AppAction(ActionId.TRADE_ADD_ADDRESS, null, null);
                actions.add(action);
            }

        } else if (appPage.getPageId() == PageId.FAVORITE) {
            int skuId = RandomNumeric.nextInteger(1, MAX_SKU_ID);
            for (int i = 0; i < 3; i++) {
                if (isFavoriteCancel) {
                    action = new AppAction(ActionId.FAVORITE_CANEL, ItemType.SKU_ID, skuId + i + "");
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
