/**
 * @Date        : 2021-04-10 18:16:03
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : -
 */
package me.shy.rt.dataware.demo.datamocker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

import me.shy.rt.dataware.demo.datamocker.actionlogmocker.LogMocker;
import me.shy.rt.dataware.demo.datamocker.businessdatamocker.DatabaseMocker;
import me.shy.rt.dataware.demo.datamocker.businessdatamocker.config.DatabaseMockerConfig;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class MallDataMockerApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(MallDataMockerApplication.class, args);
        // MockerTask mockerTask = ctx.getBean(MockerTask.class);
        // LogMocker logMocker = ctx.getBean(LogMocker.class);
        // mockerTask.mainTask(logMocker);
        // DatabaseMocker databaseMocker = ctx.getBean(DatabaseMocker.class);
        // mockerTask.mainTask(databaseMocker);

        DatabaseMockerConfig bean = ctx.getBean(DatabaseMockerConfig.class);
        System.err.println(bean);
    }

}
