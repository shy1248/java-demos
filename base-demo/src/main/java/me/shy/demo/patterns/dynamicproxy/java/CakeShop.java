/**
 * @Since: 2019-07-26 09:25:35
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 * @LastTime: 2019-07-26 10:30:25
 */

package me.shy.demo.patterns.dynamicproxy.java;

import java.lang.reflect.Proxy;

public class CakeShop {
    public static void main(String[] args) {
        ChessCakeMaker chessCakeMaker = new ChessCakeMaker();
        AddChocolateHandler chocolateHandler = new AddChocolateHandler(chessCakeMaker);
        CakeMakeable cakeMaker = (CakeMakeable)Proxy
            .newProxyInstance(chessCakeMaker.getClass().getClassLoader(), chessCakeMaker.getClass().getInterfaces(),
                chocolateHandler);

        System.out.println(cakeMaker instanceof Proxy);

        System.out.println(cakeMaker instanceof CakeMakeable);

        cakeMaker.make();

        cakeMaker.show();
    }
}
