/**
 * @Since: 2019-07-26 09:15:53
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 * @LastTime: 2019-07-26 10:23:47
 */

package me.shy.base.patterns.dynamicproxy.java;

public class FruitCakeMaker implements CakeMakeable {

    @Override public void make() {
        System.out.println("Product new FruitCake!");
    }

    @Override public void show() {
        System.out.println("FruitCake is show!");
    }

}
