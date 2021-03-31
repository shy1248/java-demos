/**
 * @Date        : 2021-02-11 15:55:32
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : Sort Dog by food.
 */
package me.shy.dp.strategy;

public class DogFoodComparator implements ComparableStrategy<Dog>{

    @Override
    public int comparareTo(Dog o1, Dog o2) {
        if (o1.getFood() < o2.getFood()) {
            return -1;
        } else if (o1.getFood() > o2.getFood()) {
            return 1;
        } else {
            return 0;
        }
    }
}
