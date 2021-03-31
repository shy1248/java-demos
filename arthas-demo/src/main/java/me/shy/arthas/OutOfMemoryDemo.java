/**
 * @Date        : 2020-12-05 22:59:52
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : Java 各种内存溢出例子
 *
 * 1.堆溢出:        java.lang.OutOfMemoryError: Java heap space
 * 2.栈溢出:        java.lang.OutOfMemorryError
 * 3.栈溢出：       java.lang.StackOverFlowError
 * 4.元信息溢出:    java.lang.OutOfMemoryError: Metaspace
 * 5.堆外内存溢出:  java.lang.OutOfMemoryError: Direct buffer memory
 * 6.GC超限:      java.lang.OutOfMemoryError: GC overhead limit exceeded
 */
package me.shy.arthas;

import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class OutOfMemoryDemo {
    public static void main(String[] args) {
        OutOfMemoryDemo demo = new OutOfMemoryDemo();
        // demo.productHeapOOM();
        // demo.infiniteRun();
        // demo.productStackOverFlowError();
        // demo.productMetaspaceOOM();
        // demo.productDirectBufferMemoryOOM();
        demo.productGCOverheadOOM();
    }

    // 第一种，堆内存溢出异常。即堆内对象不能进行回收了，堆内存持续增大，这样达到了堆内存的最大值
    // 下面通过往 List 中不停的增加新对象实现
    // 这种情况的解决方法就是找到问题点，分析哪个地方是否存储了大量类没有被回收的情况，通过 jmap 命令将线上的堆内存导出来后使用 jhat 进行分析
    public void productHeapOOM() {
        List<OOMObject> list = new ArrayList<OOMObject>();
        int counter = 0;
        try {
            while (true) {
                // 每次增加 1M
                list.add(new OOMObject());
                counter++;
                // 休眠 1s，可通过 jmc 分析
                // TimeUnit.MILLISECONDS.sleep(1000);
            }
        } catch (Throwable t) {
            t.printStackTrace();
            System.out.println(String.format("Total running count is: %s", counter));
        }
    }

    // 占内存溢出
    // 下面通过无限创建线程来实现
    // 如果真的需要创建非常多的线程，我们需要调整帧栈的大小-Xss512k，默认帧栈大小为1M，如果设置小了，可以创建更多线程
    // 这种情况，我们需要了解什么地方创建了很多线程，线上程序需要用jstack命令，将当前线程的状态导出来放到文件里边，然后将文件上传到fastthread.io网站上进行分析。
    public void infiniteRun() {
        try {
            while (true) {
                new Thread(() -> {
                    System.out.println(String.format("New thread: %d", Thread.currentThread().getId()));
                    try {
                        TimeUnit.HOURS.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
                // 休眠 1s，可通过 jmc 分析
                // TimeUnit.MILLISECONDS.sleep(1000);
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    // 栈溢出的另一种情况，这就是栈的StackOverFlow的情况
    // 下面就是一个死循环递归调用
    // 程序每次递归的时候，程序会把数据结果压入栈，包括里边的指针等，这个时候就需要帧栈大一些才能承受住更多的递归调用。
    // 通过-Xss进行设置，上边的例子需要设置小一些，以分配更多的帧栈，这次是一个帧栈需要记录程序数据，所以需要更大的值。
    // 需要通过jstack将线程数据导到文件进行分析。找到递归的点，如果程序就是需要递归的次数的话，那么这个时候就需要增大帧栈的大小以适应程序。
    public void productStackOverFlowError() {
        productStackOverFlowError();
    }

    // 元信息溢出
    // 元数据区域也成为方法区，存储着类的相关信息，常量池，方法描述符，字段描述符，运行时产生大量的类就会造成这个区域的溢出。
    // 我们运行的时候指定一下元数据区域的大小，设置到idea的VM options里边：-XX:MetaspaceSize=10M -XX:MaxMetaspaceSize=30M
    // 通过CBLIG大量生成类，导致Meta信息满了；JDK7的时候使用String.intern()不当，会产生大量常量数据；加载大量的jsp以及动态生成jsp文件。
    // 我们需要调整元数据空间的大小，如果调大了之后还出现了这种异常，我们需要分析哪里出现的溢出并fix掉。
    public void productMetaspaceOOM() {
        while (true) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(OOMObject.class);
            enhancer.setUseCache(false);
            enhancer.setCallback(new MethodInterceptor() {
                @Override
                public Object intercept(Object arg0, Method arg1, Object[] arg2, MethodProxy arg3) throws Throwable {
                    return arg3.invokeSuper(arg0, arg2);
                }
            });
            enhancer.create();
        }
    }

    // 直接内存溢出，我们除了使用堆内存外，我们还可能用直接内存，即堆外内存。
    // NIO为了提高性能，避免在Java Heap和 Native Heap中切换，所以使用直接内存，默认情况下，直接内存的大小和对内存大小一致。
    // 堆外内存不受JVM的限制，但是受制于机器整体内存的大小限制。
    // 如下代码设置堆最大内存为128m，直接内存为100m，然后我们每次分配1M放到list里边。
    // 设置 JVM 参数：-Xmx128m -XX:MaxDirectMemorySize=100M 运行
    // 我们需要检查一下程序里边是否使用的NIO及NIO，比如Netty，里边的直接内存的配置。
    public void productDirectBufferMemoryOOM() {
        final int _1_M = 1024 * 1024 * 1;
        List<ByteBuffer> list = new ArrayList<ByteBuffer>();
        int counter = 0;
        try {
            while (true) {
                // 每次分配 1M 的堆外内存
                ByteBuffer buffer = ByteBuffer.allocateDirect(_1_M);
                list.add(buffer);
                counter++;
                // 休眠 1s，可通过 jmc 分析
                // TimeUnit.MILLISECONDS.sleep(1000);
            }
        } catch (Throwable t) {
            t.printStackTrace();
            System.out.println(String.format("Total running count is: %s", counter));
        }
    }

    // JDK1.6之后新增了一个错误类型，如果堆内存太小的时候会报这个错误。
    // 如果98%的GC的时候回收不到2%的时候会报这个错误，也就是最小最大内存出现了问题的时候会报这个错误。如代码配置了最小最大堆内存都为10m。
    // 这里创建了一个线程池，如果线程池执行的时候如果核心线程处理不过来的时候会把数据放到 LinkedBlockingQueue 里边，也就是堆内存当中。
    // 这个时候我们需要检查 -Xms -Xmx 最小最大堆配置是否合理。再一个 dump 出当前内存来分析一下是否使用了大量的循环或使用大量内存代码。
    public void productGCOverheadOOM() {
        try {
            ExecutorService executor = Executors.newFixedThreadPool(5);
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                executor.execute(() -> {
                    try {
                        TimeUnit.MILLISECONDS.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static class OOMObject {}
}
