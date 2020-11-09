package me.shy.demo.concurrency.c_007;

import java.util.concurrent.TimeUnit;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: 程序在执行过程中，如果出现异常，默认情况锁会被释放 所以，在并发处理的过程中，有异常要多加小心，不然可能会发生不一致的情况。
 *               比如，在一个webapp处理过程中，多个servlet线程共同访问同一个资源，这时如果异常处理不合适，
 *               在第一个线程中抛出异常，其他线程就会进入同步代码区，有可能会访问到异常产生时的数据。 因此要非常小心的处理同步业务逻辑中的异常
 */
public class T {

    int count = 0;

    public static void main(String[] args) {
        T t = new T();
        new Thread(t::m, "t1").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(t::m, "t2").start();
    }

    synchronized void m() {
        System.out.println(Thread.currentThread().getName() + " start.");
        while (true) {
            count++;
            System.out.println(Thread.currentThread().getName() + " count " + count);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (count == 5) {
                // 此处抛出异常，锁将被释放，要想不被释放，可以在这里进行catch，然后让循环继续
                int i = 1 / 0;
                System.out.println(i);
            }
        }
    }
}
