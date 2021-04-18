/**
 * @Date        : 2021-04-13 22:45:39
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 业务数据库模拟器数据配置
 */

package me.shy.rt.dataware.datamocker.config;

import java.time.LocalDate;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import lombok.Data;

@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "mocker", ignoreInvalidFields = true)
public class DataMockerConfig {
    /** 业务日期，格式：yyyy-MM-dd */
    public LocalDate businessDate = LocalDate.now();
    /** 客户端并发数 */
    public Integer appClientConcurrence = 100;
    /** 日志收集类型：DB，LOG，KAFKA，HTTP */
    public String collectType = "LOG";
    /** 当日志收集类型为 http 时，此处指定 http 服务的地址 */
    public String httpUrl = "http://localhost:8888/report";
    /** 是否清除数据库中已存在的数据 */
    public Boolean isClear = Boolean.TRUE;
    /** 生成的新用户数量 */
    public Integer userLogonCount = 10;
    /** 男性用户比例 */
    public Integer maleUserRate = 30;
    /** 用户数据变化概率 */
    public Integer userInfoUpdateRate = 20;
    /** 最大收藏数量 */
    public Integer totalFavoriteCount = 100;
    /** 收藏取消比例 */
    public Integer cancelFavoritedRate = 10;
    /** 用户收藏商品的比例 */
    public Integer favoritedRate = 30;
    /** 每个用户添加购物车的概率 */
    public Integer perUserInCartRate = 10;
    /** 每次每个用户最多添加多少种商品进购物车 */
    public Integer maxSkuTypeInCart = 8;
    /** 用户添加商品进购物车的比例 */
    // public Integer inCartRate = 10;
    /** 每个商品最多买几个 */
    public Integer maxSkuNumberInCart = 3;
    /** 购物车中增加购买数量的比例 */
    public Integer addSkuNumberInCartRate = 10;
    /** 购物车中减少购买数量的比例 */
    public Integer minusSkuNumberInCartRate = 10;
    /** 从购物车中移除的比例 */
    public Integer removedSkuFromCartRate = 10;
    /** 购物车来源比例，用户查询：商品推广：智能推荐：促销活动 */
    public Integer[] sourceTypeOfInCartRates = { 60, 20, 10, 10 };
    /** 用户下单比例 */
    public Integer perUserOrderedRate = 30;
    /** 用户购买商品比例 */
    public Integer perSkuOrderedRate = 50;
    /** 是否参加活动 */
    public Boolean orderJoinActivity = Boolean.FALSE;
    /** 是否使用优惠券 */
    public Boolean orderUsedCoupon = Boolean.FALSE;
    /** 退单比例 */
    public Integer orderBackRate = 30;
    /** 退款原因比例，质量问题：商品描述与实际描述不一致：缺货：号码不合适：拍错：不想买了：其他 */
    public Integer[] orderBackReasonRates = { 30, 10, 20, 5, 15, 5, 5 };
    /** 购物券领取人数 */
    public Integer usersCountOfGotCoupon = 100;
    /** 用户领用优惠券的概率 */
    public Integer gotCouponUserRate = 25;
    /** 支付比例 */
    public Integer paymentRate = 70;
    /** 支付方式，支付宝：微信：银联 */
    public Integer[] paymentTypeRates = { 60, 30, 10 };
    /** 评价级别比例，好：中：差：自动 */
    public Integer[] commentGradeRates = { 30, 10, 10, 50 };
    /** 设备 id 最大值 */
    public Integer maxDeviceId = 500;
    /** 用户 id 最大值 */
    public Integer maxUserId = 500;
    /** 最大优惠券 id */
    public Integer maxCouponId = 3;
    /** 商品 id 最大值 */
    public Integer maxSkuId = 10;
    /** 页面浏览时长最大值，ms */
    public Integer maxPageDuration = 20000;
    /** App 出错的比例 */
    public Integer errorsTriggeredRate = 3;
    /** 日志间隔最大时间 */
    public Integer maxLogsInterval = 100;
    /** 新增收货地址的比例 */
    public Integer newDeliveryAddressRate = 15;
    /** 最小曝光的数量 */
    public Integer minDisplayCount = 4;
    /** 最大曝光的数量 */
    public Integer maxDisplayCount = 10;
    /** 最大活动的数量 */
    public Integer maxActivityCount = 2;
    /** 最大的位置 id */
    public Integer maxPostionId = 5;
    /** 商品详情来源类型比例，用户查询：商品推广：智能推荐：促销活动 */
    public Integer[] skuDetailSourceTypeRates = { 40, 25, 15, 20 };
    /** 搜索关键字 */
    public String[] searchKeywords = { "图书", "小米", "iphone11", "电视", "口红", "PS5", "苹果手机", "小米盒子" };
}
