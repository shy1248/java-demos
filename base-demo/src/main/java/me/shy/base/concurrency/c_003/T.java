package me.shy.base.concurrency.c_003;

import java.util.concurrent.TimeUnit;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description:
 * t1线程执行m1方法，开始睡10s，在这过程之中，t2线程执行m2方法，5s之后打印了m2；由此可见在m1执行的过程之中，m2是可以运行的。
 * 同步方法的执行过程中，非同步方法是可以执行的。
 * 只有synchronized这样的方法在运行时候才需要申请那把锁，而别的方法是不需要申请那把锁的。
 */
public class T {

    public static void main(String[] args) {
        T t = new T();

        // lambda 表达式的 2 种写法
        new Thread(t::m1, "t1").start();
        new Thread(t::m2, "t2").start();
        // new Thread(() -> t.m1(), "t1").start();
        // new Thread(() -> t.m2(), "t2").start();
    }

    public synchronized void m1() {
        System.out.println(Thread.currentThread().getName() + " m1 start.");
        try {
            TimeUnit.MILLISECONDS.sleep(10000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " m1 end.");
    }

    public void m2() {
        System.out.println(Thread.currentThread().getName() + " m2 start.");
        try {
            TimeUnit.MILLISECONDS.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " m2 end.");
    }
}
