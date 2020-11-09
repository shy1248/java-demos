package me.shy.demo.concurrency.c_018;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description:
 *
 * Queue：队列，在并发容器里面最重要的也是应用的最多的容器；有很多种实现，ConcurrentLinkedQueue，BlockingQueue；
 * 常见操作：
 * offer: 类似于add方法，但是add方法加的时候会出问题，如果有容量的限制话add就会抛异常；offer不会抛异常，返回值boolean代表是否加成功；
 * poll(): 从头部拿出来一个元素，同时把原来的删掉；
 * peek(): 从头部拿出来一个，但是原来的不删；
 *
 */
public class ConLinkedQueue {

    public static void main(String[] args) {
        Queue<String> strs = new ConcurrentLinkedQueue<>();

        for (int i = 0; i < 10; i++) {
            strs.offer("a" + i);  //add
        }

        System.out.println(strs);

        System.out.println(strs.size());

        System.out.println(strs.poll());
        System.out.println(strs.size());

        System.out.println(strs.peek());
        System.out.println(strs.size());

        //双端队列Deque
    }

}
