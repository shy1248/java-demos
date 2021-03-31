/**
 * @Date        : 2020-10-30 16:54:19
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : -
 */
package me.shy.spring;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "data")
public class GitAutoRefreshConfig {
    private String env;
    private User user;

    @Data
    @Component
    public static class User {
        private String username;
        private String password;
  }
}
