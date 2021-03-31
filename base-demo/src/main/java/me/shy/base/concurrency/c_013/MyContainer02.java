package me.shy.base.concurrency.c_013;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 *
 *               曾经的面试题：（淘宝？） 实现一个容器，提供两个方法，add，size
 *               写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，当个数到5个时，线程2给出提示并结束
 *
 *               给lists添加volatile之后，t2能够接到通知，但是，t2线程的死循环很浪费cpu，如果不用死循环，该怎么做呢？
 *
 *               这里使用wait和notify做到，wait会释放锁，而notify不会释放锁
 *               需要注意的是，运用这种方法，必须要保证t2先执行，也就是首先让t2监听才可以
 *
 *               阅读下面的程序，并分析输出结果
 *
 *               可以读到输出结果并不是size=5时t2退出，而是t1结束时t2才接收到通知而退出 想想这是为什么？
 *
 *
 *               1）解释wait和notify,notifyAll方法：  wait:让正在运行的线程进入等待状态，并且释放锁
 *               notify:唤醒某个正在等待的线程，不能精确换新某个线程 notifyAll:唤醒所有正在等待的线程
 *               2）为什么size=5了，t2线程没有结束？ 由于notify不会释放锁，即便你通知了t2,让它起来了，它起来之后想往下运行，
 *               wait了之后想重新继续往下运行是需要重新得到lock这把锁的，可是很不幸的是t1已经把这个锁锁定了，所以只有等t1执行完了，t2才会继续执行。
 *
 *               notify之后，t1必须释放锁，t2退出后，也必须notify，通知t1继续执行 整个通信过程比较繁琐
 */
public class MyContainer02 {

    volatile List<Object> list = new ArrayList<Object>();

    public static void main(String[] args) {
        final Object lock = new Object();
        MyContainer02 container = new MyContainer02();
        new Thread(() -> {
            synchronized (lock) {
                System.out.println("t2 start");
                if (container.size() != 5) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("t2 end");

                // 通知t1继续执行
                lock.notify();

            }

        }, "t2").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            synchronized (lock) {
                for (int i = 0; i < 10; i++) {
                    container.add(new Object());
                    System.out.println("add " + i);
                    if (container.size() == 5) {
                        lock.notify();

                        // 释放锁，让t2得以执行
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }, "t1").start();

    }

    public int size() {
        return this.list.size();
    }

    public void add(Object o) {
        this.list.add(o);
    }
}
