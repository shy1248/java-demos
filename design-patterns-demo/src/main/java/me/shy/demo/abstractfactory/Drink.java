/**
 * @Date        : 2021-02-12 15:29:36
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : -
 */
package me.shy.demo.abstractfactory;

public abstract class Drink {
    private String name;

    abstract void showName();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
