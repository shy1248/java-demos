/**
 * @Date        : 2021-02-10 22:28:59
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 单例模式 ---- 懒加载版3，加锁实现线程安全，但是由于在整个 getInstcane 方法上加锁，因此效率不高
 *                             为了提升效率，选择缩小加锁的范围，但是不能解决线程安全问题
 */
package me.shy.dp.singleton;

import java.util.concurrent.TimeUnit;

public class SingletonDemo05 {
    public static SingletonDemo05 INSTANCE;

    private SingletonDemo05() {
    }

    public static SingletonDemo05 getInstance() {
        if (null == INSTANCE) {
            // 此处虽然缩小了加锁范围，但是由于判断实例和创建实例非原子操作
            // 和 SingletonDemo03 一样会造成线程不安全
            synchronized (SingletonDemo05.class) {
                try {
                    TimeUnit.MILLISECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                INSTANCE = new SingletonDemo05();
            }
        }
        return INSTANCE;
    }

    public static void main(String[] args) {

        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                System.out.println(String.format("Instance[%s] hascode ===> %s.", Thread.currentThread().getId(),
                        SingletonDemo05.getInstance().hashCode()));
            }).start();
        }
    }
}
