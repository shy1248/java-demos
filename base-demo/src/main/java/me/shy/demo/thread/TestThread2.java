package me.shy.demo.thread;

public class TestThread2 {
    public static void main(String[] args) {
        Runner2 r = new Runner2();
        //r.run();  //方法调用
        //Thread t = new Thread(r);
        r.start();
        for (int i = 0; i < 100; i++) {
            System.out.println("main thread:------" + i);
        }
    }
}

class Runner2 extends Thread {
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("Runner2 :" + i);
        }
    }
}