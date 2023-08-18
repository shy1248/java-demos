/**
 * @Date        : 2021-04-18 17:35:45
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : App 埋点日志收集服务，吐入 Kafka
 */

package me.shy.rt.dataware.logconnector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LogConnectorApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogConnectorApplication.class, args);
	}

}
