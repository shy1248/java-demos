/**
 * @Date        : 2021-02-11 15:48:51
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : Sort Cat by weight.
 */
package me.shy.demo.strategy;

public class CatWeightComparator implements ComparableStrategy<Cat>{

    @Override
    public int comparareTo(Cat o1, Cat o2) {
        if (o1.getWeight() < o2.getWeight()) {
            return -1;
        } else if (o1.getWeight() > o2.getWeight()) {
            return 1;
        } else {
            return 0;
        }
    }
}
