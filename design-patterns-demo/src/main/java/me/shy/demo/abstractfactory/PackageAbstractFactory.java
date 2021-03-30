/**
 * @Date        : 2021-02-12 15:44:06
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : -
 */
package me.shy.demo.abstractfactory;

public interface PackageAbstractFactory {

    Food newFood(String name);

    Drink newDrink(String name);
}
