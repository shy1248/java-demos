package me.shy.demo.concurrency.c_006;

import java.util.concurrent.TimeUnit;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: 重入锁的另外一种情形，继承中子类的同步方法调用父类的同步方法
 * <p>
 * 一个同步方法可以调用另外一个同步方法，一个线程已经拥有某个对象的锁，再次申请的时候仍然会得到该对象的锁.
 * 也就是说synchronized获得的锁是可重入的 这里是继承中有可能发生的情形，子类调用父类的同步方法
 */
public class T {

    public static void main(String[] args) {
        new TT().m();
    }

    public synchronized void m() {
        System.out.println("m start.");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("m end.");
    }

}

class TT extends T {

    @Override public synchronized void m() {
        System.out.println("child m start.");
        super.m();
        System.out.println("child m end.");
    }
}
