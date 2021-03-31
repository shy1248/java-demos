/**
 * @Date        : 2021-02-12 14:37:17
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : A Static Factory Method for Food, singleton.
 */
package me.shy.dp.factorymeathod;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;

public class FoodFactory {

    private FoodFactory() {
    }

    public static FoodFactory getInstance() {
        return FoodFactory.InstanceHoldor.INSTANCE;
    }

    public Food buildFood(Class<? extends Food> clasz, String name) {
        System.out.println("Starting make food ...");
        Food food = null;
        try {
            TimeUnit.MILLISECONDS.sleep(100);
            food = clasz.getDeclaredConstructor().newInstance();
            food.setName(name);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException | InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Food is maked done.");
        return food;
    }

    private static class InstanceHoldor {
        private static final FoodFactory INSTANCE = new FoodFactory();
    }

}
