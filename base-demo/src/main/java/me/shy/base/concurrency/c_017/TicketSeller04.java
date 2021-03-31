package me.shy.base.concurrency.c_017;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 *
 * 使用ConcurrentQueue提高并发性
 *
 * 这里面没有加锁，同样的也有判断，但是这个不会出问题；为什么？
 * 因为在做了s==null判断后，再也没有对队列进行修改操作；（上个程序都是做了判断之后，需要对队列进行修改操作remove一下）
 * 假如A线程执行完String s = tickets.poll()，还没有来得及执行if(s==null) break就被打断了，另外一个线程把队列拿空了，
 * 大不了while(true)返过头来再拿一遍得到null，所以不会出问题；
 *
 */
public class TicketSeller04 {

    static Queue<String> tickets = new ConcurrentLinkedQueue<>();

    static {
        for (int i = 0; i < 1000; i++) {
            tickets.add("Ticket: " + i);
        }
    }

    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                while (true) {
                    String s = tickets.poll();

                    try {
                        TimeUnit.MILLISECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (s == null) {
                        break;
                    } else {
                        System.out.println("Selled: " + s);
                    }
                }
            }).start();
        }
    }

}
