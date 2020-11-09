package me.shy.demo.concurrency.c_013;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: - 曾经的面试题：（淘宝？） 实现一个容器，提供两个方法，add，size
 *               写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，当个数到5个时，线程2给出提示并结束
 *
 *               分析下面这个程序，能完成这个功能吗？
 *
 */
public class MyContainer01 {

    // 如果不添加 volatile 关键字，则无法实现上面的需求
    // 但是即使加了 volatile 关键字，还存在下面的问题：
    // 由于没加同步,c.size()等于5的时候，假如另外一个线程又往上增加了1个，实际上这时候已经等于6了才break，所以不是很精确；
    // 浪费CPU，t2线程的死循环很浪费cpu
    /* volatile */ List<Object> list = new ArrayList<Object>();

    public static void main(String[] args) {
        MyContainer01 container = new MyContainer01();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                container.add(new Object());
                System.out.println("add " + i);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t1").start();

        new Thread(() -> {
            while (true) {
                if (container.size() == 5) {
                    break;
                }
            }
            System.out.println("t2 end");
        }, "t2").start();
    }

    public int size() {
        return this.list.size();
    }

    public void add(Object o) {
        this.list.add(o);
    }
}
