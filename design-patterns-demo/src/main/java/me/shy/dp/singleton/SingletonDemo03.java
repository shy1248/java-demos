/**
 * @Date        : 2021-02-10 21:42:15
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 单例模式 ---- 懒加载版1，线程不安全
 */
package me.shy.dp.singleton;

import java.util.concurrent.TimeUnit;

public class SingletonDemo03 {

    public static SingletonDemo03 INSTANCE;

    private SingletonDemo03() {
    }

    public static SingletonDemo03 getInstance() {
        // 多线程并发的情况下，如果第一个线程判断实例为空且尚处于休眠过程中，未来得急创建实例
        // 第二个线程亦会判断实例为空，因此第二个线程也会进去到实例创建的逻辑中
        // 这样就会导致创建出多个实例了
        if (null == INSTANCE) {
            try {
                TimeUnit.MILLISECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            INSTANCE = new SingletonDemo03();
        }
        return INSTANCE;
    }

    public static void main(String[] args) {

        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                System.out.println(String.format("Instance[%s] hascode ===> %s.", Thread.currentThread().getId(),
                    SingletonDemo03.getInstance().hashCode()));
            }).start();
        }
    }
}
