/**
 * @Since: 2019-07-26 09:25:35
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 * @LastTime: 2019-07-26 14:38:55
 */

package me.shy.base.patterns.dynamicproxy.cglib;

import net.sf.cglib.proxy.Enhancer;

public class CakeShop {
    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(FruitCakeMaker.class);
        enhancer.setCallback(new AddChocolateInterceptor(new FruitCakeMaker()));
        CakeMakeable proxy = (CakeMakeable)enhancer.create();
        proxy.make();
    }
}
