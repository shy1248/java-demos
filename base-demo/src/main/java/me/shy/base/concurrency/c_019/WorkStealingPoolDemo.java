package me.shy.base.concurrency.c_019;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description:
 *
 *  工作窃取线程池：本来执行完自己的线程应该变为等待状态，但是这个会去别的线程里面拿任务执行
 *  workStealing用于什么场景：就说任务分配的不是很均匀，有的线程维护的任务队列比较长，有些
 * 线程执行完任务就结束了不太合适，所以他执行完了之后可以去别的线程维护的队列里去偷任务，这样效率更高。
 */
public class WorkStealingPoolDemo {

    public static void main(String[] args) throws IOException {
        ExecutorService service = Executors.newWorkStealingPool();
        int count = Runtime.getRuntime().availableProcessors();    //看cpu多少核的;如果是4核，默认就帮你起4个线程
        System.out.println(count);

        service.execute(new R(1000));
        for (int i = 0; i < count; i++) {
            service.execute(new R(2000));
        }

        //由于产生的是精灵线程（守护线程、后台线程），主线程不阻塞的话，看不到输出
        System.in.read();
    }

    static class R implements Runnable {

        int time;

        R(int t) {
            this.time = t;
        }

        @Override public void run() {
            try {
                TimeUnit.MILLISECONDS.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(time + " " + Thread.currentThread().getName());
        }
    }

}
