/**
 * @Date        : 2021-04-11 17:08:38
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : Append logs to file with log framwork.
 */
package me.shy.rt.dataware.demo.datamocker.actionlogmocker.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogUtil {
    public static void log(String message) {
        log.info(message);
    }
}
