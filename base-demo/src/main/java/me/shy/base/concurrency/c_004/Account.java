package me.shy.base.concurrency.c_004;

import java.util.concurrent.TimeUnit;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: 脏读
 * 主线程里面第一次读zhangsan里面的钱是0.0，第二次读是100.0；原因是set修改钱的时候过程中，sleep了2s钟；为什么sleep
 * 2s就是放大了在线程的执行过程之中的时间差，set钱方法里面this.name=name和this.balance=balance之间可能是会被别的程序执行的；
 * 在线程的执行过程set钱之中，尽管写的这个方法set加上了synchronized锁定了这个对象，锁定这个对象过程之中，它仍然有可能被那些非锁定的方法/非同步方法访问的；
 * 尽管对写进行了加锁，但是由于没有对读加锁，那么有可能会读到在写的过程中还没有完成的数据，产生了脏读问题；
 */
public class Account {

    String name;
    double balance;

    public static void main(String[] args) {
        Account a = new Account();
        new Thread(() -> a.set("zhangsan", 100)).start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(a.getBalance());

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(a.getBalance());
    }

    public synchronized void set(String name, double balance) {
        this.name = name;
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.balance = balance;
    }

    // 不加锁，产生脏读
    public /*synchronized*/ double getBalance() {
        return this.balance;
    }
}
