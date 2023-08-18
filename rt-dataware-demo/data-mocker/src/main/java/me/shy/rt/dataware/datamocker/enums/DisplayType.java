/**
 * @Date        : 2021-04-11 03:03:13
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : App 商品曝光类型
 */
package me.shy.rt.dataware.datamocker.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum DisplayType {
    PROMOTION("商品推广"), RECOMMEND("算法推荐商品"), QUERY("查询结果商品"), ACTIVITY("促销活动");

    String description;
}
