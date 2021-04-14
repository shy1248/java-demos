/**
 * @Date        : 2021-04-11 20:43:33
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : -
 */
package me.shy.rt.dataware.datamocker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import me.shy.rt.dataware.datamocker.config.DataMockerConfig;

@Component
public class MockerTask {
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
}
