/**
 * @Date        : 2021-02-11 11:46:33
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : Test Client
 */
package me.shy.dp.strategy;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Random;

import me.shy.dp.utils.ConfigLoadStrategy;
import me.shy.dp.utils.YamlConfigLoader;

@SuppressWarnings("unchecked")
public class TestClient {
    public static void main(String[] args)
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
            NoSuchMethodException, SecurityException, ClassNotFoundException {
        ConfigLoadStrategy<ComparatorStatrgyConfig> configLoader = new YamlConfigLoader<ComparatorStatrgyConfig>();
        ComparatorStatrgyConfig config = configLoader.load(
                TestClient.class.getClassLoader().getResourceAsStream("resources/application.yaml"),
                ComparatorStatrgyConfig.class);

        System.out.println(config.getCatComparatorClass());

        Cat[] cats = new Cat[5];
        Dog[] dogs = new Dog[5];

        Random random = new Random();

        for (int i = 1; i < 6; i++) {
            cats[i-1] = new Cat(random.nextInt(10) + 1, random.nextInt(10) + 1);
            dogs[i-1] = new Dog(random.nextInt(10) + 1);
        }

        System.out.println(String.format("Orignals cats sort: %s", Arrays.asList(cats)));
        System.out.println(String.format("Orignals dogs sort: %s", Arrays.asList(dogs)));
        Sorter<Cat> catSorter = new Sorter<>();

        catSorter.sort(cats, (ComparableStrategy<Cat>) Class.forName(config.getCatComparatorClass())
                .getDeclaredConstructor().newInstance());
        System.out.println(String.format("Sort Cats by weight: %s", Arrays.asList(cats)));

        catSorter.sort(cats, new CatHeightComparator());
        System.out.println(String.format("Sort Cats by Height: %s", Arrays.asList(cats)));

        Sorter<Dog> dogSorter = new Sorter<>();
        dogSorter.sort(dogs, new DogFoodComparator());
        System.out.println(String.format("Sort Dogs by Food: %s", Arrays.asList(dogs)));
    }
}
