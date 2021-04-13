/**
 * @Date        : 2021-04-10 22:22:36
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : A tool for random generated email addresses.
 */

package me.shy.rt.dataware.demo.datamocker.common;

public class RandomEmail {
    public static final String[] EMAIL_SUFFIX = { "@gmail.com", "@yahoo.com", "@msn.com", "@hotmail.com", "@aol.com",
            "@ask.com", "@live.com", "@qq.com", "@0355.net", "@163.com", "@163.net", "@263.net", "@3721.net",
            "@yeah.net", "@googlemail.com", "@126.com", "@sina.com", "@sohu.com", "@yahoo.com.cn" };
    public static final String VALID_CHARS = "abcdefghijklmnopqrstuvwxyz0123456789";

    public static String nextEmailAddress(int minLenOfName, int maxLenOfName) {
        int lenOfName = RandomNumeric.nextInteger(minLenOfName, maxLenOfName);
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < lenOfName; i++) {
            buffer.append(VALID_CHARS.charAt((int) (Math.random() * VALID_CHARS.length())));
        }
        buffer.append(EMAIL_SUFFIX[(int) (Math.random() * EMAIL_SUFFIX.length)]);
        return buffer.toString();
    }
}
