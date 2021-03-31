/**
 * @Date        : 2020-12-05 22:12:13
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : concurrent 包 Lock 错误使用，导致死锁例子
 */
package me.shy.arthas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockDeadDemo {

    public static void main(String[] args) {
        final DeadLockBean deadLockBean = new DeadLockBean();
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                    deadLockBean.productDeadLock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }

        }, "Thread-A");

        Thread threadB = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.MILLISECONDS.sleep(510);
                    deadLockBean.productDeadLock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }

        }, "Thread-B");

        threadA.start();
        threadB.start();

        System.out.println("Waitting for thread to join...");
        try {
            threadA.join();
            threadB.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("All threads has been joined.");
    }

    public static class DeadLockBean {
        private Lock lock = new ReentrantLock();

        public void productDeadLock() throws Throwable {
            System.out.println(String.format("[%s] is entering method...", Thread.currentThread().getName()));
            // locked
            lock.lock();
            try {
                System.out.println(String.format("[%s] locked...", Thread.currentThread().getName()));
                // throw new Throwable("A Throwable object has thrown..."); // 关键代码1，抛出 Throwable，会死锁，不会在catch(Exception e)中被捕获到
                throw new Exception("An Exception object has thrown..."); // 关键代码2，抛出 Exception，不会死锁，会在catch(Exception e)中被捕获，嵌套lock.unlock()并释放
            } catch (Exception e) {
                System.out.println(String.format("[%s] try to release lock in catch block.", Thread.currentThread().getName()));
                lock.unlock(); // 关键代码行3，不建议在这里释放，假如发生【关键代码行1】会产生死锁
            } finally {
                // System.out
                        // .println(String.format("[%s] try to release lock in finally block.", Thread.currentThread().getName()));
                // lock.unlock(); // 关键代码行4，无论发生何种异常，均会释放锁，推荐在此处释放锁
            }

            lock.unlock(); // 关键代码行5，假如发生不能捕获异常，将跳出方法体，不执行此处
            System.out.println(String.format("[%s] try to release lock out of try-catch block.", Thread.currentThread().getName()));
        }
    }

}
