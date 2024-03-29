/**
 * @Date        : 2021-04-11 18:03:11
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : -
 */
package me.shy.rt.dataware.datamocker;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import me.shy.rt.dataware.datamocker.util.RandomNumeric;
import me.shy.rt.dataware.datamocker.util.RandomWeightOption;
import me.shy.rt.dataware.datamocker.util.RandomWeightOption.Builder;
import me.shy.rt.dataware.datamocker.bean.actionlog.AppActionLog;
import me.shy.rt.dataware.datamocker.bean.actionlog.AppCommonLog;
import me.shy.rt.dataware.datamocker.bean.actionlog.AppDisplayLog;
import me.shy.rt.dataware.datamocker.bean.actionlog.AppLog;
import me.shy.rt.dataware.datamocker.bean.actionlog.AppPageLog;
import me.shy.rt.dataware.datamocker.bean.actionlog.AppStartLog;
import me.shy.rt.dataware.datamocker.bean.actionlog.AppLog.AppLogBuilder;
import me.shy.rt.dataware.datamocker.config.DataMockerConfig;
import me.shy.rt.dataware.datamocker.enums.Page;
import me.shy.rt.dataware.datamocker.service.CartInfoService;
import me.shy.rt.dataware.datamocker.service.CommentInfoService;
import me.shy.rt.dataware.datamocker.service.FavoriteInfoService;
import me.shy.rt.dataware.datamocker.service.OrderBackService;
import me.shy.rt.dataware.datamocker.service.OrderInfoService;
import me.shy.rt.dataware.datamocker.service.PaymentInfoService;
import me.shy.rt.dataware.datamocker.service.UserInfoService;
import me.shy.rt.dataware.datamocker.util.HttpUtil;
import me.shy.rt.dataware.datamocker.util.KafkaUtil;
import me.shy.rt.dataware.datamocker.util.LogUtil;

@Slf4j
@Component
public class Mocker implements Runnable {
    @Autowired
    DataMockerConfig config;
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    FavoriteInfoService favoriteInfoService;
    @Autowired
    CartInfoService cartInfoService;
    @Autowired
    OrderInfoService orderInfoService;
    @Autowired
    PaymentInfoService paymentInfoService;
    @Autowired
    OrderBackService orderBackService;
    @Autowired
    CommentInfoService commentInfoService;

    private String mockType;
    private LocalDate businessDate;

    public void mockBusinessData() {
        log.warn("开始生成用户信息数据...");
        userInfoService.genUserInfos(config.isClear);
        log.warn("开始生成商品收藏数据...");
        favoriteInfoService.genFavoriteSkus(config.isClear);
        log.warn("开始生成购物车数据...");
        cartInfoService.genCartInfo(config.isClear);
        log.warn("开始生成订单数据...");
        orderInfoService.genOrderInfos(config.isClear);
        log.warn("开始生成支付信息数据...");
        paymentInfoService.genPayments(config.isClear);
        log.warn("开始生成退单数据...");
        orderBackService.genRefundsOrFinish(config.isClear);
        log.warn("开始生成评论数据...");
        commentInfoService.genComments(config.isClear);
    }

    @SuppressWarnings("unchecked")
    public List<AppLog> mockAppLogs() {
        List<AppLog> appLogs = new ArrayList<>();
        Long timestamp = businessDate.atTime(LocalTime.now()).toInstant(ZoneOffset.of("+8")).toEpochMilli();
        // 启动日志，1：通用信息日志，2：错误日志，3：启动日志
        AppLogBuilder startLogBuilder = AppLog.builder();
        // 通用信息日志
        AppCommonLog appCommonLog = AppCommonLog.newInstance(config);
        startLogBuilder.common(appCommonLog);
        // 错误日志
        startLogBuilder.nextError(config);
        // 启动日志
        AppStartLog appStartLog = AppStartLog.newInstance();
        startLogBuilder.start(appStartLog);
        startLogBuilder.timestamp(timestamp);
        appLogs.add(startLogBuilder.build());

        // 开始构建页面访问日志
        Builder<List<String>> pageNamesOptionBuilder = RandomWeightOption.<List<String>>builder();
        // 读取页面概率配置文件
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("pages.json")) {
            String pathConfigJson = IOUtils.toString(in, "utf-8");
            // 解析页面概率配置文件中的 JSON
            List<HashMap<String, Object>> pathConfigMap = (List<HashMap<String, Object>>) JSON
                    .parseArray(pathConfigJson, new HashMap<String, Object>().getClass());
            // 构建页面随机选择组
            for (Map<String, Object> map : pathConfigMap) {
                List<String> pageNames = (List<String>) map.get("pages");
                Integer rate = (Integer) map.get("rate");
                pageNamesOptionBuilder.add(pageNames, rate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 随机抽取一组页面
        List<String> pageNames = pageNamesOptionBuilder.build().nextPayload();
        // 行为日志，1：主行为日志，2：曝光日志，3：错误日志
        Page lastPage = null;
        for (String pageName : pageNames) {
            // 页面访问日志
            AppLogBuilder pageLogBuilder = AppLog.builder().common(appCommonLog);
            int pageDuration = RandomNumeric.nextInteger(1000, config.maxPageDuration);
            Page page = EnumUtils.getEnum(Page.class, pageName);
            AppPageLog appPageLog = AppPageLog.newInstance(config, page, lastPage, pageDuration);
            pageLogBuilder.page(appPageLog);
            lastPage = appPageLog.getPage();
            // 主行为日志
            List<AppActionLog> appActionLogs = AppActionLog.batchInstances(config, appPageLog, timestamp, pageDuration);
            if (appActionLogs.size() > 0) {
                pageLogBuilder.actionLogs(appActionLogs);
            }
            // 曝光日志
            List<AppDisplayLog> appDisplayLogs = AppDisplayLog.batchInstances(config, appPageLog);
            if (appDisplayLogs.size() > 0) {
                pageLogBuilder.displayLogs(appDisplayLogs);
            }
            pageLogBuilder.timestamp(timestamp);
            // 错误日志
            pageLogBuilder.nextError(config);
            appLogs.add(pageLogBuilder.build());
        }
        return appLogs;
    }

    @Override
    public void run() {
        if (this.mockType.equals("DB")) {
            mockBusinessData();
        } else {
            List<AppLog> appLogs = mockAppLogs();
            for (AppLog appLog : appLogs) {
                if (this.mockType.equals("HTTP")) {
                    HttpUtil.get(config.httpUrl, appLog.toString());
                } else if (this.mockType.equals("KAFKA")) {
                    KafkaUtil.send(appLog.toString());
                } else {
                    LogUtil.log(appLog.toString());
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(RandomNumeric.nextInteger(50, config.maxLogsInterval) + 0L);
                } catch (InterruptedException e) {
                    Thread.interrupted();
                }
            }
        }
    }

    public String getMockType() {
        return mockType;
    }

    public void setMockType(String mockType) {
        this.mockType = mockType.toUpperCase();
    }

    public LocalDate getBusinessDate() {
        return businessDate;
    }

    public void setBusinessDate(LocalDate businessDate) {
        this.businessDate = businessDate;
    }
}
