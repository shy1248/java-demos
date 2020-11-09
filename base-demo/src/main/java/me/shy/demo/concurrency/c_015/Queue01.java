package me.shy.demo.concurrency.c_015;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 *
 *               面试题：写一个固定容量同步容器，拥有put和get方法，以及getCount方法，
 *               能够支持2个生产者线程以及10个消费者线程的阻塞调用
 *
 *               使用wait和notify/notifyAll来实现
 *
 *               1.为什么用while而不用if？
 *               假设容器中已经满了，如果用的是if，这个线程A发现list.size()==max已经满了，就this.wait()住了；
 *               如果容器中被拿走了元素，线程A被叫醒了，它会从this.wait()开始继续往下运行，准备执行lists.add(),
 *               可是它被叫醒了之后还没有往里扔的时候，另外一个线程往list里面扔了一个，线程A拿到锁之后不再进行if判断，而是继续执行lists.add()就会出问题了；
 *               如果用while，this.wait()继续往下执行的时候需要在while中再检查一遍，就不会出问题；  
 *               2.put()方法中为什么使用notifyAll而不是notify？
 *               如果使用notify，notify是叫醒一个线程，那么就有可能叫醒的一个线程又是生产者，整个程序可能不动了，都wait住了；
 *
 */
public class Queue01<T> {

    final private LinkedList<T> queue = new LinkedList<T>();
    final private int maxQueue = 10;
    private int count = 0;

    public static void main(String[] args) {
        Queue01<String> q = new Queue01<String>(); // 最多10个元素

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

    synchronized void put(T t) {
        while (this.queue.size() >= maxQueue) { // 想想为什么用while而不是用if？
            // System.out.println("Queue is full, waitting...");
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        queue.add(t);
        count++;
        // System.out.println(Thread.currentThread().getName() + " put, queue size is: "
        // + queue.size());
        this.notifyAll(); // 通知消费者线程进行消费
    }

    synchronized T get() {
        T t = null;
        while (this.queue.size() <= 0) {
            // System.out.println("Queue is empty, waitting...");
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        t = queue.removeFirst();
        count--;
        // System.out.println(Thread.currentThread().getName() + " get, queue size is: "
        // + queue.size());
        this.notifyAll(); // 通知生产者进行生产
        return t;
    }

}
