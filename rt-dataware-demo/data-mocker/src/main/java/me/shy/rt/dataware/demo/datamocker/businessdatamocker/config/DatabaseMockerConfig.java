/**
 * @Date        : 2021-04-13 22:45:39
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 业务数据库模拟器数据配置
 */

package me.shy.rt.dataware.demo.datamocker.businessdatamocker.config;

import java.time.LocalDate;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import lombok.Data;

@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "mocker.business-data", ignoreInvalidFields = true)
public class DatabaseMockerConfig {
    private LocalDate businessDate;
    private String isClear;
    private String userClear;
    private String userCount;

}
