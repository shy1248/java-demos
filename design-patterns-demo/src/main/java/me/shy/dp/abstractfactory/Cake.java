/**
 * @Date        : 2021-02-12 14:42:57
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : -
 */
package me.shy.dp.abstractfactory;

public class Cake extends Food {

    @Override
    void showName() {
        System.out.println(String.format("Cake[name=%s]", this.getName()));
    }
}
