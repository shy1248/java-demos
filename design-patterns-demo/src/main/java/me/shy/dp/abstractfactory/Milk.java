/**
 * @Date        : 2021-02-12 15:32:45
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : -
 */
package me.shy.dp.abstractfactory;

public class Milk extends Drink {

    @Override
    void showName() {
        System.out.println(String.format("Milk[name=%s]", this.getName()));
    }

}
