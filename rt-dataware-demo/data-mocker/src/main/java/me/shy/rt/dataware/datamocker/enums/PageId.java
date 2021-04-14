/**
 * @Date        : 2021-04-11 03:04:26
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : App 页面
 */
package me.shy.rt.dataware.datamocker.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PageId {
    HOME("首页"), CATEGORY("分类页"), DISCOVERY("发现页"), TOP_N("热门排行"), FAVORITE("收藏页"), SEARCH("搜索页"), GOODS_LIST("商品列表页"),
    GOODS_DETAIL("商品详情"), GOODS_SPEC("商品规格"), COMMENT("评价"), COMMENT_DONE("评价完成"), COMMENT_LIST("评价列表"), CART("购物车"),
    TRADE("下单结算"), PAYMENT("支付页面"), PAYMENT_DONE("支付完成"), ORDERS_ALL("全部订单"), ORDERS_UNPAID("订单待支付"),
    ORDERS_UNDELIVERED("订单待发货"), ORDERS_UNRECEIPTED("订单待收货"), ORDERS_WAIT_COMMENT("订单待评价"), MINE("我的"), ACTIVITY("活动"),
    LOGIN("登录"), REGISTER("注册");

    String description;
}
