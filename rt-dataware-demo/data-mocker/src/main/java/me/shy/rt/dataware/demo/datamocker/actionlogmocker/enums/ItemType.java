/**
 * @Date        : 2021-04-11 02:59:00
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : The page's item type of App client.
 */
package me.shy.rt.dataware.demo.datamocker.actionlogmocker.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ItemType {
    SKU_ID("商品SKUID"), KEYWORD("搜索关键词"), SKU_IDS("多个商品SKUID"), ACTIVITY_ID("活动ID"), COUPON_ID("购物券ID");

    String description;
}
