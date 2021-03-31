/**
 * @Date        : 2021-02-14 17:08:01
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : Flyweight，享元模式，表示复用对象的意思，即共享元数据的意思，JDK 的 String 类就使用的该模式（会维护一个常量池）
 */
package me.shy.dp.flyweight;

public class TestClient {
    public static void main(String[] args) {
        String str1 = "abc";
        String str2 = "abc";
        String str3 = new String("abc");
        String str4 = new String("abc");

        System.out.println(str1 == str2);
        System.out.println(str1 == str3);
        System.out.println(str2.intern() == str3.intern());
        System.out.println(str3 == str4);
        System.out.println(str3.intern() == str4.intern());
    }
}
