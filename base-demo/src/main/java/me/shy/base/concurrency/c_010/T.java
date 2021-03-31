package me.shy.base.concurrency.c_010;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description:
 *
 * 解决同样的问题的更高效的方法，使用AtomXXX类
 * AtomXXX类本身方法都是原子性的，但不能保证多个方法连续调用是原子性的
 *
 * incrementAndGet(): 原子方法，你可以认为它是加了synchronized的，当然它内部实现不是用synchronized的而是用系统相当底层的实现来去完成的；
 * 它的效率要比synchronized高很多；
 */
public class T {

    AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) {
        T t = new T();
        List<Thread> threads = new ArrayList<Thread>();

        for (int i = 0; i < 10000; i++) {
            threads.add(new Thread(t::m, "Thread-" + i));
        }

        threads.forEach(o -> o.start());

        threads.forEach(o -> {
            try {
                o.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println(t.count);
    }

    void m() {
        for (int i = 0; i < 10; i++) {
            count.incrementAndGet();
        }
    }

}
