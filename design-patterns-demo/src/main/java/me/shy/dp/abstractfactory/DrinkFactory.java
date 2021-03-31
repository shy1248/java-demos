/**
 * @Date        : 2021-02-12 14:37:17
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : A Static Factory Method for Drink, singleton.
 */
package me.shy.dp.abstractfactory;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;

public class DrinkFactory {

    private DrinkFactory() {
    }

    public static DrinkFactory getInstance() {
        return DrinkFactory.InstanceHoldor.INSTANCE;
    }

    public Drink buildDrink(Class<? extends Drink> clasz, String name) {
        System.out.println("Starting make drink ...");
        Drink drink = null;
        try {
            TimeUnit.MILLISECONDS.sleep(100);
            drink = clasz.getDeclaredConstructor().newInstance();
            drink.setName(name);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException | InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Drink is maked done.");
        return drink;
    }

    private static class InstanceHoldor {
        private static final DrinkFactory INSTANCE = new DrinkFactory();
    }

}
