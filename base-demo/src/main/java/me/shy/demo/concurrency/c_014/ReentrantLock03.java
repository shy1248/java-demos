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
 * 使用reentrantlock可以进行“尝试锁定”tryLock，这样无法锁定，或者在指定时间内无法锁定，线程可以决定是否继续等待
 */
public class ReentrantLock03 {

    Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        ReentrantLock03 rl = new ReentrantLock03();
        new Thread(rl::m1).start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(rl::m2).start();
    }

    void m1() {
        try {
            lock.lock();
            for (int i = 0; i < 10; i++) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 使用tryLock进行尝试锁定，不管锁定与否，方法都将继续执行
     * 可以根据tryLock的返回值来判定是否锁定
     * 也可以指定tryLock的时间，由于tryLock(time)抛出异常，所以要注意unclock的处理，必须放到finally中
     */
    void m2() {

        // boolean locked = lock.tryLock();
        // System.out.println("m2 ..." + locked);
        // if (locked) {
        //     lock.unlock();
        // }

        boolean isLocked = false;
        try {
            isLocked = lock.tryLock(5, TimeUnit.SECONDS);
            System.out.println("m2 " + isLocked);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (isLocked) {
                lock.unlock();
            }
        }
    }

}
