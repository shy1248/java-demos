/**
 * @Date        : 2021-02-10 22:52:33
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 单例模式 ---- 懒加载版4，加锁实现线程安全，但是由于在整个 getInstcane 方法上加锁，因此效率不高
 *                             为了提升效率，选择缩小加锁的范围，同时使用双重监测机制来保证线程安全
 */
package me.shy.demo.singleton;

import java.util.concurrent.TimeUnit;

public class SingletonDemo06 {

    // 必须采用 volatile 来禁止 JIT 和 CPU 的指令重排序，以保证双重检测有效性
    public static volatile SingletonDemo06 INSTANCE;

    private SingletonDemo06() {

    }

    public static SingletonDemo06 getInstance() {
        // 采用双重检测机制来保证实例化时的线程安全
        // 第一实例检测是非常有必要的，因为可以避免大部分的线程进入到锁内，因此可以提升效率
        if (null == INSTANCE) {
            synchronized (SingletonDemo06.class) {
                // 第二次实例检测也是必须的，因为实例检测和实例化操作非原子性，线程不安全
                // 详见 SingletonDemo05 的实现
                if (null == INSTANCE) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    INSTANCE = new SingletonDemo06();
                }
            }
        }
        return INSTANCE;
    }

    public static void main(String[] args) {

        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                System.out.println(String.format("Instance[%s] hascode ===> %s.", Thread.currentThread().getId(),
                        SingletonDemo06.getInstance().hashCode()));
            }).start();
        }
    }
}
