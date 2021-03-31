package me.shy.base.concurrency.c_016;

import java.util.concurrent.TimeUnit;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: ThreadLocal线程局部变量
 *
 * ThreadLocal的意思就是，tl里面的变量，自己的线程自己用；
 * 你别的线程里要想用的话，不好意思你自己往里扔；不能用我线程里面放的东西；
 * 相当于每个线程都有自己的变量，互相之间不会产生冲突；
 * 可以理解为person对象每个线程里面拷贝了一份，改的都是自己那份，都是自己线程本地的变量，所以空间换时间；ThreadLocal在效率上会更高一些；
 * 有一些需要加锁的对象，如果它们在使用的时候自己进行的改变，自己维护这个状态，不用通知其他线程，那么这个时候可以使用ThreadLocal；
 *
 */
public class T {

    // 现在这两个线程是互相影响的；第二个线程改了名字之后，第一个线程就能读的到了；
    // 有的时候就想线程2的改变，不想让线程1知道，这时候怎么做？
    //volatile static Person p = new Person();

    // ThreadLocal是使用空间换时间，synchronized是使用时间换空间
    // 比如在hibernate中session就存在与ThreadLocal中，避免synchronized的使用
    // 运行下面的程序，理解ThreadLocal

    static ThreadLocal<Person> tl = new ThreadLocal<Person>();

    public static void main(String[] args) {

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(tl.get());
        }).start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tl.set(new Person());
        }).start();
    }
}

class Person {

    String name = "Tom";
}
