/**
 * @Since: 2019-07-26 09:17:49
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 * @LastTime: 2019-07-26 10:23:18
 */

package me.shy.demo.patterns.dynamicproxy.java;

public class ChessCakeMaker implements CakeMakeable {

    @Override public void make() {
        System.out.println("Make new ChessCake!");
    }

    @Override public void show() {
        System.out.println("ChessCake is show!");
    }

}
