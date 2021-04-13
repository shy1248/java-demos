/**
 * @Date        : 2021-04-12 23:10:23
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 常量
 */
package me.shy.rt.dataware.demo.datamocker.businessdatamocker.constant;

public class Constant {
    /** 退货状态：审批中 */
    public static final String BACK_STATUS_APPROVING = "0701";
    /** 退货状态：审批通过 */
    public static final String BACK_STATUS_APPROVED = "0702";
    /** 退货状态：审批不通过 */
    public static final String BACK_STATUS_DENY = "0703";
    /** 退货状态：已发货 */
    public static final String BACK_STATUS_SEND = "0704";
    /** 退货状态：退款完成 */
    public static final String BACK_STATUS_FINISH = "0705";
    /** 退货状态：退款失败 */
    public static final String BACK_STATUS_FAIL = "0706";

    /** 订单状态：未支付 */
    public static final String ORDER_STATUS_UNPAID = "1001";
    /** 订单状态：已支付 */
    public static final String ORDER_STATUS_PAID = "1002";
    /** 订单状态：已取消 */
    public static final String ORDER_STATUS_CANCEL = "1003";
    /** 订单状态：已完成 */
    public static final String ORDER_STATUS_FINISH = "1004";
    /** 订单状态：退款中 */
    public static final String ORDER_STATUS_BACK = "1005";
    /** 订单状态：退款完成 */
    public static final String ORDER_STATUS_BACK_DONE = "1006";

    /** 支付类型：支付宝 */
    public static final String PAYMENT_TYPE_ALIPAY = "1101";
    /** 支付类型：微信 */
    public static final String PAYMENT_TYPE_WECHAT = "1102";
    /** 支付类型：银联 */
    public static final String PAYMENT_TYPE_UNION = "1103";

    /** 商品评价：好评 */
    public static final String APPRAISE_GOOD = "1201";
    /** 商品评价：中评 */
    public static final String APPRAISE_SOSO = "1202";
    /** 商品评价：差评 */
    public static final String APPRAISE_BAD = "1203";
    /** 商品评价：自动 */
    public static final String APPRAISE_AUTO = "1204";

    /** 退货原因类别：质量问题 */
    public static final String BACK_REASON_BAD_GOODS = "1301";
    /** 退货原因类别：商品描述与实际描述不一致 */
    public static final String BACK_REASON_WRONG_DESC = "1302";
    /** 退货原因类别：缺货 */
    public static final String BACK_REASON_SALE_OUT = "1303";
    /** 退货原因类别：号码不合适 */
    public static final String BACK_REASON_SIZE_ISSUE = "1304";
    /** 退货原因类别：拍错 */
    public static final String BACK_REASON_MISTAKE = "1305";
    /** 退货原因类别：不想买了 */
    public static final String BACK_REASON_NO_REASON = "1306";
    /** 退货原因类别：其他 */
    public static final String BACK_REASON_OTHER = "1307";

    /** 优惠券状态：未使用 */
    public static final String COUPON_STATUS_UNUSED = "1401";
    /** 优惠券状态：使用中 */
    public static final String COUPON_STATUS_USING = "1402";
    /** 优惠券状态：已使用 */
    public static final String COUPON_STATUS_USED = "1403";

    /** 退款类型：仅退款 */
    public static final String BACK_TYPE_ONLY_MONEY = "1501";
    /** 退款类型：退货退款 */
    public static final String BACK_TYPE_WITH_GOODS = "1502";

    /** 来源类型：用户查询 */
    public static final String SOURCE_TYPE_QUREY = "2401";
    /** 来源类型：商品推广 */
    public static final String SOURCE_TYPE_PROMOTION = "2402";
    /** 来源类型：智能推荐 */
    public static final String SOURCE_TYPE_AUTO_RECOMMEND = "2403";
    /** 来源类型：促销活动 */
    public static final String SOURCE_TYPE_ACTIVITY = "2404";

    /** 优惠券使用范围：三级分类商品 */
    public static final String COUPON_RANGE_TYPE_CATEGORY3 = "3301";
    /** 优惠券使用范围：特定商标 */
    public static final String COUPON_RANGE_TYPE_TRADEMARK = "3302";
    /** 优惠券使用范围：通用 */
    public static final String COUPON_RANGE_TYPE_SPU = "3303";

    /** 优惠券类型：满减 */
    public static final String COUPON_TYPE_MJ = "3201";
    /** 优惠券类型：满量打折 */
    public static final String COUPON_TYPE_ML = "3202";
    /** 优惠券类型：代金券 */
    public static final String COUPON_TYPE_DJ = "3203";

    /** 活动规则：满减 */
    public static final String ACTIVITY_RULE_TYPE_MJ = "3101";
    /** 活动规则：满量打折 */
    public static final String ACTIVITY_RULE_TYPE_ML = "3102";
    /** 活动规则：折扣 */
    public static final String ACTIVITY_RULE_TYPE_ZK = "3103";
}
