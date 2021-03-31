/**
 * @Date        : 2020-12-05 20:49:26
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : synchronized 实现死锁例子
 *
 * 线程 Thread-A 先获取锁 objA，然后在同步块里嵌套竞争锁 objB;
 * 而线程 Thread-B 先获取锁 objB，然后在同步块里嵌套竞争锁 objA;
 * 此时已经被线程 Thread-A 拥有，而 Thread-A 在等待锁 objB，而锁 objB 被 Thread-B 拥有，Thread-B 在等待锁 objA …… 无限循环
 *
 *
 */
package me.shy.arthas;

import java.util.concurrent.TimeUnit;

public class SyncDeadLockDemo {
    private static Object objA = new Object();
    private static Object objB = new Object();

    private void deadLock() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(String.format("[%s] try to get Lock ==> objA", Thread.currentThread().getName()));
                synchronized (objA) {
                    System.out.println(String.format("[%s] have got Lock ==> objA", Thread.currentThread().getName()));
                    try {
                        TimeUnit.MILLISECONDS.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out
                            .println(String.format("[%s] try to get Lock ==> objB", Thread.currentThread().getName()));
                    synchronized (objB) {
                        // never output
                        System.out.println(
                                String.format("[%s] have got Lock ==> objB", Thread.currentThread().getName()));
                    }
                }
            }
        }, "Thread-A").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(String.format("[%s] try to get Lock ==> objB", Thread.currentThread().getName()));
                synchronized (objB) {
                    System.out.println(String.format("[%s] have got Lock ==> objB", Thread.currentThread().getName()));
                    try {
                        TimeUnit.MILLISECONDS.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out
                            .println(String.format("[%s] try to get Lock ==> objA", Thread.currentThread().getName()));
                    synchronized (objA) {
                        // never output
                        System.out.println(
                                String.format("[%s] have got Lock ==> objA", Thread.currentThread().getName()));
                    }
                }
            }
        }, "Thread-B").start();
    }

    public static void main(String[] args) {
        new SyncDeadLockDemo().deadLock();
    }
}
