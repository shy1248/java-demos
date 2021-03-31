/**
 * @Date        : 2020-10-30 16:21:28
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : -
 */

package me.shy.spring;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
// 自动刷新
@RefreshScope
// 定时任务
@EnableScheduling
public class DemoConfigClientApplication {

    private GitConfig gitConfig;
    private GitAutoRefreshConfig gitAutoRefreshConfig;
    private ContextRefresher refresher;

    @Autowired
    public DemoConfigClientApplication(GitConfig gitConfig, GitAutoRefreshConfig gitAutoRefreshConfig, ContextRefresher refresher) {
        this.gitConfig = gitConfig;
        this.gitAutoRefreshConfig = gitAutoRefreshConfig;
        this.refresher = refresher;
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoConfigClientApplication.class, args);
    }

    @GetMapping("/show")
    public GitConfig show() {
        return gitConfig;
    }

    @GetMapping("/autoRefreshShow")
    public GitAutoRefreshConfig autoRefreshShow() {
        return gitAutoRefreshConfig;
    }

    // 异步执行刷新
    @Async
    // 每10秒种执行一次
    @Scheduled(cron = "0/10 * * * * ?")
    public void freshCronJob() {
        System.err.println("Cron table running ...");
        Set<String> resp = refresher.refresh();
        if (!resp.isEmpty()) {
            System.err.println(resp);
        }
    }
}
