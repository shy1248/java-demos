/**
 * @Date        : 2021-04-11 12:25:11
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : App client log mocker configurations.
 */
package me.shy.rt.dataware.demo.datamocker.actionlogmocker.config;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import me.shy.rt.dataware.demo.datamocker.common.ParameterValidator;

@Configuration
public class AppConfig {
    public static LocalDate MOCKER_DATE = null;
    public static Integer MOCKER_COUNT = 1000;
    public static String MOCKER_TYPE = "log";
    public static String MOCKER_URL = "http://localhost:80";
    public static Integer MAX_DEVICE_ID = 500;
    public static Integer MAX_UID = 500;
    public static Integer MAX_COUPON_ID = 3;
    public static Integer MAX_SKU_ID = 10;
    public static Integer PAGE_DURATION_TIME = 20000;
    public static Integer ERROR_RATE = 3;
    public static Integer LOG_SLEEP = 100;
    public static Integer FAVORITE_RATE = 30;
    public static Integer FAVORITE_CANCEL_RATE = 10;
    public static Integer CART_RATE = 10;
    public static Integer CART_ADD_NUM_RATE = 10;
    public static Integer CART_MINUS_NUM_RATE = 10;
    public static Integer CART_REMOVE_RATE = 10;
    public static Integer ADD_ADDRESS_RATE = 15;
    public static Integer GET_COUPON_RATE = 25;
    public static Integer MAX_DISPLAY_COUNT = 10;
    public static Integer MIN_DISPLAY_COUNT = 4;
    public static Integer MAX_ACTIVITY_COUNT = 2;
    public static Integer MAX_POSITION_ID = 5;
    public static Integer[] SOURCE_TYPE_RATES;
    public static String[] SEARCH_KEYWORDS;

    @Value("${mocker.actionlog.send-type}")
    public void setMockerType(String mockerType) {
        AppConfig.MOCKER_TYPE = mockerType;
    }

    @Value("${mocker.actionlog.http-url}")
    public void setMockerUrl(String mockerUrl) {
        AppConfig.MOCKER_URL = mockerUrl;
    }

    @Value("${mocker.actionlog.startup.count}")
    public void setMockerCount(String mockerCount) {
        AppConfig.MOCKER_COUNT = ParameterValidator.intValidate(mockerCount);
    }

    @Value("${mocker.actionlog.max.deviceId}")
    public void setMaxMid(String maxMid) {
        AppConfig.MAX_DEVICE_ID = ParameterValidator.intValidate(maxMid);
    }

    @Value("${mocker.actionlog.max.uid}")
    public void setMaxUid(String maxUid) {
        AppConfig.MAX_UID = ParameterValidator.intValidate(maxUid);
    }

    @Value("${mocker.actionlog.max.skuId}")
    public void setMaxSkuId(String maxSkuId) {
        AppConfig.MAX_SKU_ID = ParameterValidator.intValidate(maxSkuId);
    }

    @Value("${mocker.actionlog.page.durationTime}")
    public void setPageDurationTime(String pageDurationTime) {
        AppConfig.PAGE_DURATION_TIME = ParameterValidator.intValidate(pageDurationTime);
    }

    @Value("${mocker.actionlog.error.rate}")
    public void setErrorRate(String errorRate) {
        AppConfig.ERROR_RATE = ParameterValidator.intValidate(errorRate);
    }

    @Value("${mocker.actionlog.log.sleep}")
    public void setLogSleep(String logSleep) {
        AppConfig.LOG_SLEEP = ParameterValidator.intValidate(logSleep);
    }

    public static void setFavoriteRate(Integer favoriteRate) {
        AppConfig.FAVORITE_RATE = favoriteRate;
    }

    public static void setFavoriteCancelRate(Integer favorCancelRate) {
        AppConfig.FAVORITE_CANCEL_RATE = favorCancelRate;
    }

    public static void setCartRate(Integer cartRate) {
        AppConfig.CART_RATE = cartRate;
    }

    public static void setCartAddNumRate(Integer cartAddNumRate) {
        AppConfig.CART_ADD_NUM_RATE = cartAddNumRate;
    }

    public static void setCartMinusNumRate(Integer cartMinusNumRate) {
        AppConfig.CART_MINUS_NUM_RATE = cartMinusNumRate;
    }

    public static void setCartRemoveRate(Integer cartRemoveRate) {
        AppConfig.CART_REMOVE_RATE = cartRemoveRate;
    }

    public static void setAddAddressRate(Integer addAddressRate) {
        AppConfig.ADD_ADDRESS_RATE = addAddressRate;
    }

    public static void setMaxDisplayCount(Integer maxDisplayCount) {
        AppConfig.MAX_DISPLAY_COUNT = maxDisplayCount;
    }

    public static void setMinDisplayCount(Integer minDisplayCount) {
        AppConfig.MIN_DISPLAY_COUNT = minDisplayCount;
    }

    public static void setMaxActivityCount(Integer maxActivityCount) {
        AppConfig.MAX_ACTIVITY_COUNT = maxActivityCount;
    }

    @Value("${mocker.actionlog.business-date}")
    public void setMockDate(String mockDate) {
        AppConfig.MOCKER_DATE = ParameterValidator.dateValidate(mockDate);

    }

    @Value("${mocker.actionlog.detail.sourceTypeRate}")
    public void setSourceTypeRates(String sourceTypeRate) {
        AppConfig.SOURCE_TYPE_RATES = ParameterValidator.ratioStringVolidate(sourceTypeRate, 4);
    }

    @Value("${mocker.actionlog.search.keyword}")
    public void setSearchKeywords(String keywords) {
        AppConfig.SEARCH_KEYWORDS = ParameterValidator.arrayStringValidte(keywords);
    }

    @Value("${mocker.actionlog.getCouponRate}")
    public void setGetCouponRate(String getCouponRate) {
        AppConfig.GET_COUPON_RATE = ParameterValidator.ratioNumberValidate(getCouponRate);
    }

    @Value("${mocker.actionlog.max.couponId}")
    public void setMaxCouponId(String couponId) {
        AppConfig.MAX_COUPON_ID = ParameterValidator.intValidate(couponId);
    }
}
