package me.shy.demo.netty;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @Since: 2020/3/24 21:32
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 *
 **/
public class FutureTest {
    public static void main(String[] args) {
        ExecutorService serviceForFutureTask = Executors.newSingleThreadExecutor();
        // FutureTask 方式
        FutureTask<String> futureTask = new FutureTask<>(new Callable<String>() {
            @Override public String call() throws Exception {
                TimeUnit.SECONDS.sleep(10);
                return "Task[1] has done!";
            }
        });
        serviceForFutureTask.execute(futureTask);

        // submit方式
        ExecutorService serviceForSubmit = Executors.newSingleThreadExecutor();
        Future<String> result = serviceForSubmit.submit(new Callable<String>() {
            @Override public String call() throws Exception {
                TimeUnit.SECONDS.sleep(20);
                return "Task[2] has done!";
            }
        });

        boolean isFutureTaskDone = false;
        boolean isSubmittedDone = false;

        try {
            while (!(isFutureTaskDone && isSubmittedDone)) {
                // for FutureTask
                if(!futureTask.isDone()){
                    System.out.println("Waiting for task[1] ...");
                    TimeUnit.MILLISECONDS.sleep(500);
                } else {
                    isFutureTaskDone = true;
                    String s = futureTask.get();
                    System.out.println(s);

                    // result.cancel(true);
                }

                // for submit
                if(!result.isDone()){
                    System.out.println("Waiting for task[2] ...");
                    TimeUnit.MILLISECONDS.sleep(500);
                } else {
                    isSubmittedDone = true;
                    String s = result.get();
                    System.out.println(s);
                }
            }

            // 关闭线程池
            serviceForFutureTask.shutdown();
            serviceForSubmit.shutdown();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}
