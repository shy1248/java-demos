/**
 * @Date        : 2021-02-12 15:35:32
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : -
 */
package me.shy.dp.abstractfactory;

public class Cola extends Drink {

    @Override
    void showName() {
        System.out.println(String.format("Cola[name=%s]", this.getName()));
    }

}
