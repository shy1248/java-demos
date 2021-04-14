/**
 * @Date        : 2021-04-13 22:45:39
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 业务数据库模拟器数据配置
 */

package me.shy.rt.dataware.datamocker.config;

import java.time.LocalDate;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "mocker", ignoreInvalidFields = true)
public class DataMockerConfig {
    /** 业务日期，格式：yyyy-MM-dd */
    public static LocalDate businessDate = LocalDate.now();
    public static Integer mockerConcurrence = 100;
    /** 日志收集类型：DB，LOG，KAFKA，HTTP */
    public static String collectType = "LOG";
    /** 当日志收集类型为 http 时，此处指定 http 服务的地址 */
    public static String httpUrl = "http://localhost:8888/report";
    /** 是否清除数据库中已存在的数据 */
    public static Boolean isClear = Boolean.TRUE;
    /** 是否重置数据库中的用户 */
    public static Boolean userClear = Boolean.TRUE;
    /** 生成的新用户数量 */
    public static Integer newUserCount = 10;
    /** 男性用户比例 */
    public static Integer maleUserRate = 30;
    /** 用户数据变化概率 */
    public static Integer userUpdateRate = 20;
    /** 最大收藏数量 */
    public static Integer maxFavoriteCount = 100;
    /** 收藏取消比例 */
    public static Integer favoriteCancelRate = 10;
    /** 每个用户添加购物车的概率 */
    public static Integer cartPerUserRate = 10;
    /** 每次每个用户最多添加多少种商品进购物车 */
    public static Integer cartMaxSkuCount = 8;
    /** 每个商品最多买几个 */
    public static Integer cartMaxSkuNumber = 3;
    /** 购物车来源比例，用户查询：商品推广：智能推荐：促销活动 */
    public static Integer[] cartSourceTypeRates = { 60, 20, 10, 10 };
    /** 用户下单比例 */
    public static Integer orderUserRate = 30;
    /** 用户购买商品比例 */
    public static Integer orderSkuRate = 50;
    /** 是否参加活动 */
    public static Boolean orderJoinActivity = Boolean.FALSE;
    /** 是否使用优惠券 */
    public static Boolean orderUseCoupon = Boolean.FALSE;
    /** 退款原因比例，质量问题：商品描述与实际描述不一致：缺货：号码不合适：拍错：不想买了：其他 */
    public static Integer[] orderBackReasonRates = { 30, 10, 20, 5, 15, 5, 5 };
    /** 购物券领取人数 */
    public static Integer getCouponUserCount = 100;
    /** 支付比例 */
    public static Integer paymentRate = 70;
    /** 支付方式，支付宝：微信：银联 */
    public static Integer[] paymentTypeRates = { 60, 30, 10 };
    /** 评价级别比例，好：中：差：自动 */
    public static Integer[] commentBrandRates = { 30, 10, 10, 50 };
    /** 设备 id 最大值 */
    public static Integer maxDeviceId = 500;
    /** 用户 id 最大值 */
    public static Integer maxUserId = 500;
    /** 最大优惠券 id */
    public static Integer maxCouponId = 3;
    /** 商品 id 最大值 */
    public static Integer maxSkuId = 10;
    /** 页面浏览时长最大值，ms */
    public static Integer maxPageDuration = 20000;
    /** App 出错的比例 */
    public static Integer errorRate = 3;
    /** 日志间隔最大时间 */
    public static Integer logsInterval = 100;
    /** 用户收藏商品的比例 */
    public static Integer favoriteRate = 30;
    /** 用户添加商品进购物车的比例 */
    public static Integer inCartRate = 10;
    /** 购物车中增加购买数量的比例 */
    public static Integer cartAddNumberRate = 10;
    /** 购物车中减少购买数量的比例 */
    public static Integer cartMinusNumberRate = 10;
    /** 从购物车中移除的比例 */
    public static Integer cartRemoveRate = 10;
    /** 新增收货地址的比例 */
    public static Integer newReceviedAddressRate = 15;
    /** 用户领用优惠券的概率 */
    public static Integer userGetCouponRate = 25;
    /** 最小曝光的数量 */
    public static Integer minDisplayCount = 4;
    /** 最大曝光的数量 */
    public static Integer maxDisplayCount = 10;
    /** 最大活动的数量 */
    public static Integer maxActivityCount = 2;
    /** 最大的位置 id */
    public static Integer maxPostionId = 5;
    /** 商品详情来源类型比例，用户查询：商品推广：智能推荐：促销活动 */
    public static Integer[] skuDetailSourceTypeRates = { 40, 25, 15, 20 };
    /** 搜索关键字 */
    public static String[] searchKeywords = { "图书", "小米", "iphone11", "电视", "口红", "PS5", "苹果手机", "小米盒子" };
}
