/**
 * @Date        : 2021-04-11 20:43:33
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : -
 */
package me.shy.rt.dataware.datamocker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import me.shy.rt.dataware.datamocker.config.DataMockerConfig;

@Slf4j
@Component
public class MockerTask implements ApplicationRunner {
    @Autowired
    ThreadPoolTaskExecutor poolExecutor;

    public void mainTask(Runnable mocker) {
        for (int i = 0; i < DataMockerConfig.mockerConcurrence; i++) {
            poolExecutor.execute(mocker);
            System.out.println("active+" + poolExecutor.getActiveCount());
        }

        while (true) {
            try {
                Thread.sleep(1000);
                if (poolExecutor.getActiveCount() == 0) {
                    poolExecutor.destroy();
                    return;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void help() {
        System.out.println("");
        System.out.println("Usage: java -jar data-mocker.jar [Options]");
        System.out.println("");
        System.out.println("OPTIONS:");
        System.out.println("    --collect <COLLECTOR>    数据收集器，COLLECTOR 必须是 DB, LOG, HTTP, KAFKA 其中之一");
        System.out.println("    --date <DATE>            业务日期，日期格式必须为 yyyy-MM-dd");
        System.out.println("");
        System.exit(-128);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Set<String> appliedCollectors = new HashSet<>();
        appliedCollectors.add("DB");
        appliedCollectors.add("LOG");
        appliedCollectors.add("HTTP");
        appliedCollectors.add("KAFKA");
        Mocker mocker = new Mocker();

        List<String> collects = args.getOptionValues("collect");
        List<String> dates = args.getOptionValues("date");
        if (null != collects && collects.size() != 0) {
            String collect = collects.get(0).toUpperCase();
            if (!appliedCollectors.contains(collect)) {
                log.error("Unsuport data collector.");
                help();
            }
            mocker.setMockType(collect);
        }
        if (null != dates && dates.size() != 0) {
            String dateString = dates.get(0);
            try {
                LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                mocker.setBusinessDate(date);
            } catch (Exception e) {
                log.error("Illegal argument.");
                help();
            }
        }
        mainTask(mocker);
    }
}
