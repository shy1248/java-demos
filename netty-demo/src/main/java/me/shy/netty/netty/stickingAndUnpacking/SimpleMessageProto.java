package me.shy.netty.netty.stickingAndUnpacking;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 */
public class SimpleMessageProto {
    // 匹配任意单词字符加中文
    private static final Pattern MSG_PATTERN =
        Pattern.compile("\\[ContentLength\\]:(\\d+)\\[BODY\\]([\\w,\\u4E00-\\u9FA5].*)" + "\\[BODY\\]");

    public static String warp(String raw) {
        return "[ContentLength]:" + raw.length() + "[BODY]" + raw + "[BODY]";
    }

    public static String parse(String warpped) {
        Matcher matcher = MSG_PATTERN.matcher(warpped);
        matcher.find();
        if (!matcher.matches()) {
            return null;
        }
        String lengthString = matcher.group(1);
        String message = matcher.group(2);
        if (Integer.parseInt(lengthString) != message.length()) {
            return null;
        }
        return message;
    }
}
