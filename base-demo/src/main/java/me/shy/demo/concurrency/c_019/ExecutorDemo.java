package me.shy.demo.concurrency.c_019;

import java.util.concurrent.Executor;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 *
 * Executor执行器是一个接口，只有一个方法execute执行任务，在java的线程池的框架里边，这个是最顶层的接口；
 *
 * ExecutorService：从Executor接口继承。
 *
 * Callable：里面call方法，和Runnable接口很像，设计出来都是被其他线程调用的；
 * 但是Runnable接口里面run方法是没有返回值的也不能抛出异常；而call方法有返回值可以抛异常；
 *
 * Executors: 操作Executor的一个工具类；以及操作ExecutorService，ThreadFactory，Callable等；
 *
 */
public class ExecutorDemo implements Executor {

    public static void main(String[] args) {
        new ExecutorDemo().execute(new Runnable() {

            @Override public void run() {
                System.out.println("hello executor");
            }

        });
    }

    @Override public void execute(Runnable command) {
        //new Thread(command).run();
        command.run();
    }

}
