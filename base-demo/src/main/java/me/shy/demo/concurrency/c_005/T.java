package me.shy.demo.concurrency.c_005;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: 一个线程已经拥有某个对象的锁，再次申请的时候仍然会得到该对象的锁.
 * <p>
 * 一个同步方法可以调用另外一个同步方法，一个线程已经拥有某个对象的锁，再次申请的时候仍然会得到该对象的锁.
 * 也就是说synchronized获得的锁是可重入的.(可重入的意思就是获得锁之后还可以再获得一遍)
 * <p>
 * 对t执行m1的时候，需要在t上面加把锁，拿到这个锁了，开始执行，执行锁定的过程之中，调用了m2()；
 * 调用m2的过程中，发现m2也是需要申请一把锁，而申请的这把锁就是当前自己已经持有的这把锁；
 * 严格来讲，这把锁m1已经持有了，m2还能持有吗？由于是在同一个线程里面，这个是没关系的。
 * 它可以再去申请我自己已经拥有的这把锁，实际上就在这把锁上加个数字，从1变成2，锁定了2次。
 * 总而言之，再去申请当前持有的这把锁没问题，仍然会得到该对象的锁。
 */

import java.util.concurrent.TimeUnit;

public class T {

    public static void main(String[] args) {
        T t = new T();
        new Thread(t::m1).start();
    }

    synchronized void m1() {
        System.out.println("m1 start");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        m2();
    }

    synchronized void m2() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("m2");
    }
}