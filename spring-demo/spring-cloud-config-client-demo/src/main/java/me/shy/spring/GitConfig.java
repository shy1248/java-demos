/**
 * @Date        : 2020-10-30 16:47:37
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : -
 */
package me.shy.spring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class GitConfig {
    @Value("${data.env}")
    private String env;
    @Value("${data.user.username}")
    private String username;
    @Value("${data.user.password}")
    private String password;
}
