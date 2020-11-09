package me.shy.demo.concurrency.c_014;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: ReentrantLock还可以指定为公平锁
 *
 *公平锁：等待时间长的线程先执行
 * 竞争锁：多个线程一起竞争一个锁
 * 竞争锁相对效率高
 */
public class ReentrantLock05 extends Thread {

    private static ReentrantLock lock = new ReentrantLock(true); //参数为true表示为公平锁，请对比输出结果

    public static void main(String[] args) {
        ReentrantLock05 rl = new ReentrantLock05();
        Thread th1 = new Thread(rl);
        Thread th2 = new Thread(rl);
        th1.start();
        th2.start();
    }

    public void run() {
        for (int i = 0; i < 10000; i++) {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + " get lock");
            } finally {
                lock.unlock();
            }
        }
    }

}
