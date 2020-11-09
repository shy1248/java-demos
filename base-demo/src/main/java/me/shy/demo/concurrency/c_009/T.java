package me.shy.demo.concurrency.c_009;

import java.util.ArrayList;
import java.util.List;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 *
 *  volatile并不能保证多个线程共同修改running变量时所带来的不一致问题，也就是说volatile不能替代synchronized
 * 	运行下面的程序，并分析结果
 *
 * volatile和synchronized区别？
 * volatile只保证可见性，并不保证原子性；
 * synchronized既保证可见性，又保证原子性；但效率要比volatile低不少；
 * 如果只需要保证可见性的时候，使用volatile，不要使用synchronized；
 *  
 */
public class T {

    volatile int count = 0;

    public static void main(String[] args) {
        T t = new T();
        List<Thread> threads = new ArrayList<Thread>();
        for (int i = 0; i < 10; i++) {
            threads.add(new Thread(t::m, "Thread-" + i));
        }

        threads.forEach((o) -> o.start());
        threads.forEach((o) -> {
            try {
                o.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println(t.count);
    }

    void m() {
        for (int i = 0; i < 1000; i++) {
            count++;
        }
    }
}
