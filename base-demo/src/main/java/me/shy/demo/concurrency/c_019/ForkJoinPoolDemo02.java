package me.shy.demo.concurrency.c_019;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description:
 *
 *               和例子 ForkJoinPoolDemo01
 *               差不多，唯一的区别是有返回值了，RecursiveTask<V>中的V泛型就是返回值类型。 long result =
 *               task.join()，因为join本身就是阻塞的，只有等所有的都执行完了，最后才得出总的执行结果。
 *               所以不需要System.in.read了；
 *
 */
public class ForkJoinPoolDemo02 {

    static final int MAX_NUM = 50000;
    static int[] nums = new int[1000000];
    static Random r = new Random();

    static {
        for (int i = 0; i < nums.length; i++) {
            nums[i] = r.nextInt(100);
        }

        System.out.println(Arrays.stream(nums).sum()); // stream api
    }

    public static void main(String[] args) throws IOException {
        ForkJoinPool fjp = new ForkJoinPool();
        AddTask task = new AddTask(0, nums.length);
        fjp.execute(task);

        long result = task.join();
        System.out.println(result);
    }

    static class AddTask extends RecursiveTask<Long> {
        /**
         *
         */
        private static final long serialVersionUID = 4752771700653337829L;
        int start, end;

        AddTask(int s, int e) {
            start = s;
            end = e;
        }

        @Override protected Long compute() {

            if (end - start <= MAX_NUM) {
                long sum = 0L;
                for (int i = start; i < end; i++) {
                    sum += nums[i];
                }
                return sum;
            }

            int middle = start + (end - start) / 2;

            AddTask subTask1 = new AddTask(start, middle);
            AddTask subTask2 = new AddTask(middle, end);
            subTask1.fork();
            subTask2.fork();

            return subTask1.join() + subTask2.join();
        }
    }

}
