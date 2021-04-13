/**
 * @Date        : 2021-04-11 18:03:11
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : -
 */
package me.shy.rt.dataware.demo.datamocker.actionlogmocker;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.stereotype.Component;

import me.shy.rt.dataware.demo.datamocker.common.RandomNumeric;
import me.shy.rt.dataware.demo.datamocker.common.RandomWeightOption;
import me.shy.rt.dataware.demo.datamocker.common.RandomWeightOption.Builder;
import me.shy.rt.dataware.demo.datamocker.actionlogmocker.bean.AppAction;
import me.shy.rt.dataware.demo.datamocker.actionlogmocker.bean.AppCommon;
import me.shy.rt.dataware.demo.datamocker.actionlogmocker.bean.AppDisplay;
import me.shy.rt.dataware.demo.datamocker.actionlogmocker.bean.AppMain;
import me.shy.rt.dataware.demo.datamocker.actionlogmocker.bean.AppPage;
import me.shy.rt.dataware.demo.datamocker.actionlogmocker.bean.AppStart;
import me.shy.rt.dataware.demo.datamocker.actionlogmocker.bean.AppMain.AppMainBuilder;
import me.shy.rt.dataware.demo.datamocker.actionlogmocker.config.AppConfig;
import me.shy.rt.dataware.demo.datamocker.actionlogmocker.enums.PageId;
import me.shy.rt.dataware.demo.datamocker.actionlogmocker.util.HttpUtil;
import me.shy.rt.dataware.demo.datamocker.actionlogmocker.util.KafkaUtil;
import me.shy.rt.dataware.demo.datamocker.actionlogmocker.util.LogUtil;

@Component
public class LogMocker implements Runnable {
    private Long timestamp;

    @SuppressWarnings("unchecked")
    public List<AppMain> generateAppMains() {
        List<AppMain> appMains = new ArrayList<>();
        LocalDate businessDate = AppConfig.MOCKER_DATE;
        timestamp = businessDate.atTime(LocalTime.now()).toInstant(ZoneOffset.of("+8")).toEpochMilli();
        // 启动
        AppMainBuilder appMainBuilder = AppMain.builder();
        AppCommon appCommon = AppCommon.newInstance();
        appMainBuilder.common(appCommon);
        appMainBuilder.nextError();
        AppStart appStart = AppStart.newInstance();
        appMainBuilder.start(appStart);
        appMainBuilder.timestamp(timestamp);
        appMains.add(appMainBuilder.build());

        // 读取页面概率配置文件
        Thread.currentThread().getContextClassLoader();
        InputStream in = ClassLoader.getSystemResourceAsStream("path.json");
        String pathConfigJson = null;
        try {
            pathConfigJson = IOUtils.toString(in, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 解析页面概率配置文件中的 JSON
        List<Map<String, Object>> pathConfigMap = (List<Map<String, Object>>) JSON.parseArray(pathConfigJson,
                new HashMap<String, Object>().getClass());
        Builder<List<String>> groupBuilder = RandomWeightOption.<List<String>>builder();
        for (Map<String, Object> map : pathConfigMap) {
            List<String> paths = (List<String>) map.get("path");
            Integer rate = (Integer) map.get("rate");
            groupBuilder.add(paths, rate);
        }
        // 随机抽取一组页面
        List<String> paths = groupBuilder.build().nextPayload();
        // 逐个输入日志
        // 每条日志，1：主行为；2：曝光；3：错误
        PageId lastPageId = null;
        for (String path : paths) {
            // 构建页面
            AppMainBuilder pageBuilder = AppMain.builder().common(appCommon);
            int pageDuration = RandomNumeric.nextInteger(1000, AppConfig.PAGE_DURATION_TIME);
            PageId pageId = EnumUtils.getEnum(PageId.class, path);
            AppPage appPage = AppPage.newInstance(pageId, lastPageId, pageDuration);
            pageBuilder.page(appPage);
            lastPageId = appPage.getPageId();
            // 构建行为
            List<AppAction> appActions = AppAction.batchInstances(appPage, timestamp, pageDuration);
            if (appActions.size() > 0) {
                pageBuilder.actions(appActions);
            }
            //
            List<AppDisplay> appDisplays = AppDisplay.batchInstances(appPage);
            if (appDisplays.size() > 0) {
                pageBuilder.displays(appDisplays);
            }

            pageBuilder.timestamp(timestamp);
            pageBuilder.nextError();
            appMains.add(pageBuilder.build());
        }

        return appMains;
    }

    @Override
    public void run() {
        List<AppMain> appMains = generateAppMains();

        for (AppMain appMain : appMains) {
            if (AppConfig.MOCKER_TYPE.equals("log")) {
                LogUtil.log(appMain.toString());
            } else if (AppConfig.MOCKER_TYPE.equals("http")) {
                HttpUtil.get(appMain.toString());
            } else if (AppConfig.MOCKER_TYPE.equals("kafka")) {
                KafkaUtil.send(appMain.toString());
            }
            try {
                Thread.sleep(AppConfig.LOG_SLEEP);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
