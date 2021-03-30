/**
 * @Date        : 2021-02-12 14:56:51
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : -
 */
package me.shy.demo.abstractfactory;

public class Noodles extends Food {

    @Override
    void showName() {
        System.out.println(String.format("Noodle[name=%s]", this.getName()));
    }

}
