package me.shy.demo.concurrency.c_017;

import java.util.HashSet;
import java.util.concurrent.TimeUnit;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: 无需加锁的的单例模式实现，同时可以懒加载，内部静态类的方式
 */
public class ConcurrentSingleten {

    private ConcurrentSingleten() {
    }

    public static ConcurrentSingleten getInstance() {
        return Inner.s;
    }

    public static void main(String[] args) {
        HashSet<ConcurrentSingleten> cache = new HashSet<ConcurrentSingleten>();

        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                cache.add(ConcurrentSingleten.getInstance());
            }).start();
        }

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(cache.size());
    }

    private static class Inner {

        private static ConcurrentSingleten s = new ConcurrentSingleten();
    }
}
