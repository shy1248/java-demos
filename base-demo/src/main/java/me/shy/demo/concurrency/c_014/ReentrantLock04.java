package me.shy.demo.concurrency.c_014;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 * 使用ReentrantLock还可以调用lockInterruptibly方法，可以对线程interrupt方法做出响应，
 * 在一个线程等待锁的过程中，可以被打断
 *
 * t1线程牢牢的拿到锁之后，一直sleep不会释放，如果t2线程中的run方法使用lock.lock(),那么t2线程就会一直傻傻的等着这把锁，不能被其他线程打断；
 * 而使用lockInterruptibly()方法是可以被打断的，主线程main调用t2.interrupt()来打断t2，告诉他是不会拿到这把锁的，别等了；
 * 报错是因为lock.unlock()这个方法报错的，因为都没有拿到锁，无法unlock();是代码的问题，应该判断有锁，已经锁定的情况下才lock.unlock();
 */
public class ReentrantLock04 {

    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        Thread t1 = new Thread(() -> {
            try {
                lock.lock();
                System.out.println("t1 start");
                TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
                System.out.println("t1 end");
            } catch (InterruptedException e) {
                System.out.println("t1 interrupted");
            } finally {
                lock.unlock();
            }
        });

        t1.start();

        Thread t2 = new Thread(() -> {
            try {
                // 使用 lock.lock() t1 无法被打断
                // lock.lock();
                lock.lockInterruptibly();
                System.out.println("t2 start");
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                System.out.println("t2 interrupted");
            } finally {
                lock.unlock();
            }
        });

        t2.start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.interrupt();
    }

}
