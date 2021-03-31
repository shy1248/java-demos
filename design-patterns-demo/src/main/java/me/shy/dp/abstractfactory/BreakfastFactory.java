/**
 * @Date        : 2021-02-12 15:48:24
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : Breakfast factory, make Cake and Milk
 */
package me.shy.dp.abstractfactory;

public class BreakfastFactory implements PackageAbstractFactory {

    @Override
    public Food newFood(String name) {
        return FoodFactory.getInstance().buildFood(Cake.class, name);
    }

    @Override
    public Drink newDrink(String name) {
        return DrinkFactory.getInstance().buildDrink(Milk.class, name);
    }

}
