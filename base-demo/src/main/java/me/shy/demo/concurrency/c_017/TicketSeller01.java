package me.shy.demo.concurrency.c_017;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 *
 * 有N张火车票，每张票都有一个编号
 * 同时有10个窗口对外售票
 * 请写一个模拟程序
 *
 * 分析下面的程序可能会产生哪些问题？
 * 重复销售？超量销售？
 *
 * 可能卖重；一张票可能被多个线程同时remove(0),所以可能一张票被卖出去多次；
 * 也可能最后一张票的时候都被多个线程remove(),程序会报错，总之，不加锁是不行的。
 * ArrayList不是同步的，remove、add等各种方法全都不是同步的；一定会出问题；
 *
 */
public class TicketSeller01 {

    static List<String> tickets = new ArrayList<String>();

    static {
        for (int i = 0; i < 1000; i++) {
            tickets.add("Ticket" + i);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                while (tickets.size() > 0) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Selled: " + tickets.remove(0));
                }
            }).start();
        }
    }

}
