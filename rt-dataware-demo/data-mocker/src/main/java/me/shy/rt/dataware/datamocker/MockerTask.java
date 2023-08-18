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
    DataMockerConfig config;
    @Autowired
    ThreadPoolTaskExecutor poolExecutor;
    @Autowired
    Mocker mocker;

    private Integer concurrent;

    public void mainTask() {
        if ("DB".equals(mocker.getMockType())) {
            log.warn("开始生成业务数据...");
            new Thread(mocker).start();
        } else {
            log.warn("开始生成行为日志数据...");
            log.warn("当前日志收集器配置为：{}。", mocker.getMockType());
            if (concurrent > 5000) {
                log.warn("并发数超过5000，使用默认值100！");
                concurrent = 100;
            }
            try {
                for (int i = 0; i < concurrent; i++) {
                    poolExecutor.submit(mocker);
                }

                while (true) {
                    Thread.sleep(1000);
                    if (poolExecutor.getActiveCount() == 0) {
                        poolExecutor.destroy();
                        return;
                    }
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
        System.out.println("    --collect=<COLLECTOR>    数据收集器，COLLECTOR 必须是 DB, LOG, HTTP, KAFKA 其中之一，默认值：LOG");
        System.out.println("    --date=<DATE>            业务日期，日期格式必须为 yyyy-MM-dd，默认值：当天");
        System.out.println("    --concurrency=<VALUE>    客户端并发数，整型，默认值：100，最大值：5000");
        System.out.println("");
        System.out.println("EXAMPLES:");
        System.out.println("    java -jar data-mocker.jar --collect=LOG --date=2021-04-01 --concurrency=100");
        System.out.println("    java -jar data-mocker.jar --collect=LOG --concurrency=100");
        System.out.println("    java -jar data-mocker.jar --collect=DB --date=2021-04-01");
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

        List<String> collects = args.getOptionValues("collect");
        List<String> dates = args.getOptionValues("date");
        List<String> concurrents = args.getOptionValues("concurrence");

        if (null != collects && collects.size() != 0) {
            String collect = collects.get(0).toUpperCase();
            if (!appliedCollectors.contains(collect)) {
                log.error("Unsuport data collector.");
                help();
            }
            mocker.setMockType(collect);
        } else {
            mocker.setMockType(config.collectType);
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
        } else {
            mocker.setBusinessDate(config.businessDate);
        }

        if (null != dates && concurrents.size() != 0) {
            String concurrentString = concurrents.get(0);
            try {
                concurrent = Integer.parseInt(concurrentString);
            } catch (Exception e) {
                log.error("Illegal argument.");
                help();
            }
        } else {
            concurrent = config.appClientConcurrence;
        }

        mainTask();
    }
}
