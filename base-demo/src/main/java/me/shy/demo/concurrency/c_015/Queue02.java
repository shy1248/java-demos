package me.shy.demo.concurrency.c_015;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: - 面试题：写一个固定容量同步容器，拥有put和get方法，以及getCount方法，
 *               能够支持2个生产者线程以及10个消费者线程的阻塞调用
 *
 *               使用Lock和Condition来实现 对比两种方式，Condition的方式可以更加精确的指定哪些线程被唤醒
 *
 *               使用lock和condition好处在于可以精确的通知那些线程被叫醒，哪些线程不必被叫醒，这个效率显然要比notifyAll把所有线程全叫醒要高很多。
 */
public class Queue02<T> {

    final private LinkedList<T> queue = new LinkedList<T>();
    final private int maxQueueSize = 10;

    Lock lock = new ReentrantLock();
    Condition producer = lock.newCondition();
    Condition consumer = lock.newCondition();
    private int count = 0;

    public static void main(String[] args) {
        Queue02<String> q = new Queue02<String>(); // 最多10个元素

        // 启动消费者线程
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 5; j++) {
                    System.out.println(q.get());
                }
            }, "C" + i).start();
        }

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 启动生产者线程
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                for (int j = 0; j < 25; j++) {
                    q.put(Thread.currentThread().getName() + "" + j);
                }
            }, "P" + i).start();
        }
    }

    public T get() {
        T t = null;
        try {
            lock.lock();
            while (this.queue.size() <= 0) {
                consumer.await();
            }
            t = this.queue.removeFirst();
            count--;
            producer.signalAll(); // 通知生产者进行生产
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return t;
    }

    public void put(T t) {
        try {
            lock.lock();
            while (this.queue.size() >= maxQueueSize) {
                producer.await();
            }
            this.queue.add(t);
            count++;
            consumer.signalAll(); // 通知消费者线程进行消费
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }
}
