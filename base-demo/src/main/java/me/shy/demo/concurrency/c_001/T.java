package me.shy.demo.concurrency.c_001;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description:
 * 启动了5个线程，thread0先拿到这把锁，开始执行，thread1-4都在等待准备抢这把锁； thread0执行完之后，释放锁；
 * thread4率先抢到了这把锁，开始执行;执行完之后thread1又抢到了这把锁，开始执行....；
 * 所以看到每次线程访问一次，count-1； 而且thread执行的先后顺序每次执行的结果不同，因为你不知道哪个线程先执行了；
 */
public class T implements Runnable {

    private int count = 10;

    public static void main(String[] args) {
        T t = new T();
        for (int i = 0; i < 5; i++) {
            new Thread(t, "Thread" + i).start();
        }
    }

    public synchronized void run() {
        count--;
        System.out.println(Thread.currentThread().getName() + " count = " + count);
    }
}
