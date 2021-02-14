/**
 * @Date        : 2021-02-11 15:51:48
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : Sort Cat by height.
 */
package me.shy.demo.strategy;

public class CatHeightComparator implements ComparableStrategy<Cat> {

    @Override
    public int comparareTo(Cat o1, Cat o2) {
        if (o1.getHeight() < o2.getHeight()) {
            return -1;
        } else if (o1.getHeight() > o2.getHeight()) {
            return 1;
        } else {
            return 0;
        }
    }

}
