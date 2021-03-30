package me.shy.demo.concurrency.c_014;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description:
 *
 * jdk里面提供了一个新的锁，是手工锁，它是用来替代synchronized的，叫ReentrantLock，重入锁，
 * 其实synchronized也是可重入的，但是这把锁是和synchronized是有区别的，ReentrantLock是用新的同步方法写的时候经常用的一个工具；
 * 复习之前讲的synchronized同步：
 *
 * reentrantlock用于替代synchronized
 * 本例中由于m1锁定this,只有m1执行完毕的时候,m2才能执行
 * 这里是复习synchronized最原始的语义
 *
 * reentrantlock用于替代synchronized
 * 使用reentrantlock可以完成同样的功能
 * 需要注意的是，必须要必须要必须要手动释放锁（重要的事情说三遍）
 * 使用syn锁定的话如果遇到异常，jvm会自动释放锁，但是lock必须手动释放锁，因此经常在finally中进行锁的释放

 */
public class ReentrantLock02 {

    Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        ReentrantLock02 r = new ReentrantLock02();

        new Thread(r::m1).start();

        new Thread(r::m2).start();
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

    void m2() {
        lock.lock();
        System.out.println("m2 start.");
        lock.unlock();
    }

}
