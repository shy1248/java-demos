/**
 * @Date        : 2021-04-05 13:02:36
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : -
 */
package me.shy.flink.example.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Util {
    public final static String DEFUALT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static String ts2dts(Long ts, String pattern) {
        return DateTimeFormatter.ofPattern(pattern).format(Instant.ofEpochMilli(ts).atZone(ZoneId.systemDefault()));
    }

    public static Long dts2ts(String dts, String pattern) {
        LocalDateTime dateTime = LocalDateTime.parse(dts, DateTimeFormatter.ofPattern(pattern));
        return Instant.from(dateTime).toEpochMilli();
    }

    public static String ts2ds(Long ts) {
        return ts2dts(ts, DEFUALT_PATTERN);
    }

    public static Long dts2ts(String dts) {
        return dts2ts(dts, DEFUALT_PATTERN);
    }

}
