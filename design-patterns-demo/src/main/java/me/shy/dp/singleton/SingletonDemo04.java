/**
 * @Date        : 2021-02-10 22:24:01
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 单例模式 ---- 懒加载版2，加锁实现线程安全，但是由于在整个 getInstcane 方法上加锁，因此效率不高
 */
package me.shy.dp.singleton;

import java.util.concurrent.TimeUnit;

public class SingletonDemo04 {

    public static SingletonDemo04 INSTANCE;

    private SingletonDemo04() {
    }

    // 使用 synchronized 方法给 getInstance 方法上锁，以保证线程安全
    public static synchronized SingletonDemo04 getInstance() {
        if (null == INSTANCE) {
            try {
                TimeUnit.MILLISECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            INSTANCE = new SingletonDemo04();
        }
        return INSTANCE;
    }

    public static void main(String[] args) {

        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                System.out.println(String.format("Instance[%s] hascode ===> %s.", Thread.currentThread().getId(),
                        SingletonDemo04.getInstance().hashCode()));
            }).start();
        }
    }
}
