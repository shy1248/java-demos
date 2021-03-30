/**
 * @Since: 2019-07-26 09:15:53
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 * @LastTime: 2019-07-26 10:58:43
 */

package me.shy.demo.patterns.dynamicproxy.cglib;

public class FruitCakeMaker implements CakeMakeable {

    @Override public void make() {
        System.out.println("Product new FruitCake!");
    }

    @Override public void show() {
        System.out.println("FruitCake is show!");
    }

}
