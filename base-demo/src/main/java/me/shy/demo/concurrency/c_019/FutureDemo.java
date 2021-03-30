package me.shy.demo.concurrency.c_019;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description:
 *
 *  Future: ExecutorService里面有submit方法，它的返回值是Future类型，因为你扔一个任务进去
 * 需要执行一段时间，未来的某一个时间点上，任务执行完了产生给你一个结果，这个Future代表的就是
 * 那个Callable的返回值；
 *
 */
public class FutureDemo {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        FutureTask<Integer> task = new FutureTask<Integer>(new Callable<Integer>() {
            @Override public Integer call() throws Exception {
                TimeUnit.MILLISECONDS.sleep(3000);
                return 1000;
            }
        });

        // FutureTask<Integer> task = new FutureTask<>(() -> {
        //     TimeUnit.MILLISECONDS.sleep(3000);
        //     return 1000;
        // });

        new Thread(task).start();

        System.out.println(task.get()); //阻塞

        //*******************************
        ExecutorService service = Executors.newFixedThreadPool(5);
        Future<Integer> f = service.submit(() -> {
            TimeUnit.MILLISECONDS.sleep(5000);
            return 1;
        });
        System.out.println(f.isDone());
        System.out.println(f.get());
        System.out.println(f.isDone());

    }

}
