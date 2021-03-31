/**
 * @Date        : 2021-02-11 11:23:16
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 比较器策略接口
 */
package me.shy.dp.strategy;

public interface ComparableStrategy<T> {
    // 对象的比较方式，任何实现该策略的子类 T 都必须实现自己的比较方法
    int comparareTo(T o1, T o2);
}
