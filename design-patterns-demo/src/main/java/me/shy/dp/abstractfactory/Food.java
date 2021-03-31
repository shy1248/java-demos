/**
 * @Date        : 2021-02-12 14:53:22
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : A base class of food.
 */
package me.shy.dp.abstractfactory;

public abstract class Food {
    private String name;
    abstract void showName();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
