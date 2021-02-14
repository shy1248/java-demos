/**
 * @Date        : 2021-02-12 15:58:51
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : Dinner factory, only build Noodle and Cola.
 */
package me.shy.demo.abstractfactory;

public class DinnerFactory implements PackageAbstractFactory {

    @Override
    public Food newFood(String name) {
        return FoodFactory.getInstance().buildFood(Noodles.class, name);
    }

    @Override
    public Drink newDrink(String name) {
        return DrinkFactory.getInstance().buildDrink(Cola.class, name);
    }

}
