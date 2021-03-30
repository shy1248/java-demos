package me.shy.demo.concurrency.c_018;

import java.util.concurrent.LinkedTransferQueue;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 *
 * TransferQueue：提供了transfer方法，一般是这种情形，有一个队列，消费者线程先启动，
 * 然后生产者生产一个东西的时候不是往队列里头仍，它首先去找有没有消费者，如果有消费者，
 * 生产的东西不往队列里扔了而是直接给消费者消费；如果没有消费者的话，调用transfer线程就会阻塞；
 *  
 * 比如场景：坦克大战中多个坦克客户端链接服务器，坦克A移动了，服务端需要把A移动的位置消息
 * 发送给其他客户端，服务端存在一个消息队列，消息都交给不同的线程处理，有一种是都往消息队列里扔，
 * 然后再往外拿，不过这种太慢了；假如有一大推消费者线程等着，那么直接把消息扔给消费者线程就行了，
 * 不要再往队列里扔了，效率会更高一些；所以TransferQueue是用在更高的并发的情况下。
 *
 */
public class TransforQueueDemo {

    public static void main(String[] args) throws InterruptedException {
        LinkedTransferQueue<String> strs = new LinkedTransferQueue<>();

        // 先启动消费者
        new Thread(() -> {
            try {
                System.out.println(strs.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        strs.transfer("aaa");
        // 如果transfer换成put（或者add、offer），也不会有问题，因为不会阻塞
        // strs.put("aaa");

        // 如果先起生产者transfer，然后再起消费者take，程序就会阻塞住了
        // new Thread(() -> {
        //     try {
        //         System.out.println(strs.take());
        //     } catch (InterruptedException e) {
        //         e.printStackTrace();
        //     }
        // }).start();

    }

}
