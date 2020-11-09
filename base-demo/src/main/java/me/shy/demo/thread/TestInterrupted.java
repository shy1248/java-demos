package me.shy.demo.thread;

import java.util.Date;

public class TestInterrupted {
    public static void main(String[] args) {
        new TestInterrupted().start();
    }

    void start() {
        TRunner1 r = new TRunner1();
        Thread t = new Thread(r);
        t.start();
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t.interrupt();
        //r.flg = false;
    }

    public class TRunner1 implements Runnable {
        public void run() {
            //boolean flg = true;
            while (true) {
                try {
                    Thread.sleep(1000);
                    System.out.println("====" + new Date() + "====");
                } catch (InterruptedException e) {
                    return;
                }
            }
        }
    }
}

