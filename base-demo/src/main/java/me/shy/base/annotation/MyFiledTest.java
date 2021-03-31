/**
 * @Date        : 2020-10-18 20:30:17
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : Java Annotation
 *

 *
 */
package me.shy.base.annotation;

import java.lang.reflect.Field;

public class MyFiledTest {
    // 使用自定义注解
    @MyField(desc = "用户名", length = 24)
    private String username;

    public static void main(String[] args) {

        // 获取模版类
        Class<MyFiledTest> c = MyFiledTest.class;

        // 获取所有字段
        for (Field f : c.getDeclaredFields()) {
            // 如果字段上有加 @MyField 注解
            if (f.isAnnotationPresent(MyField.class)) {
                // 获取注解相关信息
                MyField field = f.getAnnotation(MyField.class);
                System.out.println(String.format("字段[%s], 描述:[%s], 长度:[%d]", f.getName(), field.desc(), field.length()));
            }
        }
    }
}
