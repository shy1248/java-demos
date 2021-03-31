/**
 * @Date        : 2021-02-14 17:23:39
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 建造者模式，用于构建复杂的对象
 */
package me.shy.dp.builder;

public class TestClient {
    public static void main(String[] args) {

        // can not new a instance
        // Table t0 = new Table();

        Table t1 = Table.builder().setName("T1").setLength(80).setHeight(76).setWidth(40).setCorlor("RED")
                .setShap("Radio").build();

        Table t2 = Table.builder().setName("T2").setLength(100).setHeight(76).setWidth(60).setCorlor("WHITE")
                .setShap("Radio").build();

        Table t3 = Table.builder().setName("T3").setLength(120).setHeight(76).setWidth(80).setCorlor("BLACK")
                .setShap("Radio").build();


        System.out.println(t1);
        System.out.println(t2);
        System.out.println(t3);
    }
}
