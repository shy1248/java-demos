package me.shy.base.concurrency.c_002;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description:
 * 启动了5个线程，因为每次都是new了一个t，每个线程都能锁住t，一共有5个t，5个count；
 * 所以这里5个线程执行完，count都是9；但是因为不知道哪个线程先被cpu执行，所以thread名字的顺序是随机的；
 */
public class T implements Runnable {

    private int count = 10;

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            T t = new T();
            new Thread(t, "Thread" + i).start();
        }
    }

    public synchronized void run() {
        count--;
        System.out.println(Thread.currentThread().getName() + " count= " + count);
    }

}
