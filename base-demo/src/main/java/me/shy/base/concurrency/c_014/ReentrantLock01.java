package me.shy.base.concurrency.c_014;

import java.util.concurrent.TimeUnit;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 *
 * jdk里面提供了一个新的锁，是手工锁，它是用来替代synchronized的，叫ReentrantLock，重入锁，
 * 其实synchronized也是可重入的，但是这把锁是和synchronized是有区别的，ReentrantLock是用新的同步方法写的时候经常用的一个工具；
 * 复习之前讲的synchronized同步：
 *
 * reentrantlock用于替代synchronized
 * 本例中由于m1锁定this,只有m1执行完毕的时候,m2才能执行
 * 这里是复习synchronized最原始的语义
 */

public class ReentrantLock01 {

    public static void main(String[] args) {
        ReentrantLock01 r = new ReentrantLock01();
        new Thread(r::m1).start();
        new Thread(r::m2).start();
    }

    synchronized void m1() {
        for (int i = 0; i < 10; i++) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(i);
        }
    }

    synchronized void m2() {
        System.out.println("m2 start");

    }

}
