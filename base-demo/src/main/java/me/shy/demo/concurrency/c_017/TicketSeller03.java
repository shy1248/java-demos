package me.shy.demo.concurrency.c_017;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 *
 * 就算操作A和B都是同步的，但A和B组成的复合操作也未必是同步的，仍然需要自己进行同步
 * 就像这个程序，判断size和进行remove必须是一整个的原子操作
 *
 * 相当于把判断和销售都加到了一个原子操作里去了；可以解决问题；
 * 不过加锁后效率并不是很高；每销售一张票的时候都要把整个队列tickets锁定；
 *
 */
public class TicketSeller03 {

    static List<String> tickets = new LinkedList<>();

    static {
        for (int i = 0; i < 1000; i++) {
            tickets.add("Ticket: " + i);
        }
    }

    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                while (true) {
                    synchronized (tickets) {
                        if (tickets.size() <= 0) {
                            break;
                        }

                        try {
                            TimeUnit.MILLISECONDS.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        System.out.println("Selled: " + tickets.remove(0));
                    }
                }
            }).start();
        }
    }

}
