/**
 * @Date        : 2021-04-11 02:59:00
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 行为操作对象类型
 */
package me.shy.rt.dataware.datamocker.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ItemType {
    SKU_ID("商品库存ID"), KEYWORD("搜索关键词"), SKU_IDS("多个商品库存ID"), ACTIVITY_ID("活动ID"), COUPON_ID("优惠券ID");

    String description;
}
