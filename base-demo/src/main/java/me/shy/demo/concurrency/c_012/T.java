package me.shy.demo.concurrency.c_012;

import java.util.concurrent.TimeUnit;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: 不要以字符串常量作为锁定对象
 *
 * 不要以字符串常量作为锁定对象
 * 在下面的例子中，m1和m2其实锁定的是同一个对象
 * 这种情况还会发生比较诡异的现象，比如你用到了一个类库，在该类库中代码锁定了字符串“Hello”，
 * 但是你读不到源码，所以你在自己的代码中也锁定了"Hello",这时候就有可能发生非常诡异的死锁阻塞，
 * 因为你的程序和你用到的类库不经意间使用了同一把锁
 */
public class T {

    String s1 = "Hello";
    String s2 = "Hello";

    public static void main(String[] args) {
        T t = new T();
        // m2 方法永远不会被执行，因为 m1 与 m2 锁定的是同一个对象
        new Thread(t::m1).start();
        new Thread(t::m2).start();
    }

    void m1() {
        synchronized (s1) {
            System.out.println("m1 start.");
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void m2() {
        synchronized (s2) {
            System.out.println("m2 start.");
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
