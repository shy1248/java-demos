/**
 * @Date        : 2021-04-11 02:52:59
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : App 客户端行为类型
 */
package me.shy.rt.dataware.datamocker.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ActionType {

    FAVORITE_ADD("添加收藏"), FAVORITE_CANEL("取消收藏"), CART_ADD("添加购物车"), CART_REMOVE("删除购物车"), CART_ADD_NUM("增加购物车商品数量"),
    CART_MINUS_NUM("减少购物车商品数量"), TRADE_ADD_ADDRESS("增加收货地址"), GET_COUPON("领取优惠券");

    String description;
}
