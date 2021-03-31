package me.shy.base.concurrency.c_008;

import java.util.concurrent.TimeUnit;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: volatile 关键字，使一个变量在多个线程间可见
 *
 * A B线程都用到一个变量，java默认是A线程中保留一份copy，这样如果B线程修改了该变量，则A线程未必知道
 * 使用volatile关键字，会让所有线程都会读到变量的修改值
 *
 * 在下面的代码中，running是存在于堆内存的t对象中
 * 当线程t1开始运行的时候，会把running值从内存中读到t1线程的工作区，在运行过程中直接使用这个copy，并不会每次都去
 * 读取堆内存，这样，当主线程修改running的值之后，t1线程感知不到，所以不会停止运行
 *
 * 使用volatile，将会强制所有线程都去堆内存中读取running的值
 *
 * 可以阅读这篇文章进行更深入的理解
 * http://www.cnblogs.com/nexiyi/p/java_memory_model_and_thread.html
 *
 * volatile并不能保证多个线程共同修改running变量时所带来的不一致问题，也就是说volatile不能替代synchronized
 *
 * 不加volatile是不行的，线程1没法结束，那么volatile到底是干嘛的？
 * 线程之间要让running这个值进行可见，这里要涉及到java的内存模型，java对于线程处理的内存模型；
 * 在jmm（java memory model）里面有个内存它叫主内存，我们所熟识的栈内存，堆内存都可以认为是主内存；
 * 每一个线程在执行的过程之中，它有一个线程自己的一块内存，（实际上不能认为这块是内存，有可能它是内存，
 * 还有cpu上的缓冲区，是一个统称，就是线程存放它自己变量的一块内存），如果两个cpu在运行不同线程的话，
 * 每个线程上都有自己的一块缓冲区，缓冲区就是把主内存JMM里面的内容读过来在缓冲区里面进行修改，如果+1，+1加了好多次再写回去；
 * 现在有个running在主内存里面，值是true，占一个字节；
 * 第一个线程启动的时候会把这个字节copy到自己的缓冲区里面，cpu在处理的过程之中就不再去主内存里面读了；
 * 它在运行这个线程的过程之中，由于这个cpu非常的忙，在while(running)里面，没空再去主线程里面去刷一下running值了；
 * 它一直读自己缓存里面的内容，running永远是true；
 * 第二个主线程里面，它首先也是把running读到它自己的缓冲区，然后把running改成false，发现running已经改了那就把running写回到主内存里面去；
 * 写回到主内存之后，但是第一个线程它没有在主内存重新读啊，所以第一个线程永远结束不了；
 *  
 * 加了volatile，第一个线程运行中，不是要求你每次while(running)循环的时候都要到主内存里面读一次running的值，
 * 而是说一旦主内存running这个值发生改变后会通知别的线程，说你们的缓冲区里面内容过期了请重新读一下，
 * 第一个线程再去读的时候running已经改了，所以线程结束了。
 * 加了volatile的意思就是当running改了后会通知其他的所有线程的缓冲区，说你们那边的值已经过期了，请你们再去主内存里面重新读一下。
 * 而并不是通知所有的线程cpu执行的时候每次用的时候都要去主内存读一下，不是，是写完之后进行缓存过期通知。
 *  
 * 要保证线程之间的可见性，那么需要对两个线程共同访问的变量加上volatile；如果不想加volatile那只能用synchronized；
 * 但volatile的效率要比synchronized高的多；所以在很多高并发的框架里面好多的volatile关键字都在用；
 * 比如JDK的并发容器的源码；能用volatile的时候就不要加锁，程序的并发性就要提高很多；
 */
public class T {

    // 对比一下有无volatile的情况下，整个程序运行结果的区别
    /*volatile*/ boolean running = true;

    public static void main(String[] args) {
        T t = new T();
        new Thread(t::m).start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t.running = false;
    }

    void m() {
        System.out.println("m start");
        while (running) {

        }
        System.out.println("m end");
    }

}
