package me.shy.netty.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 */
public class EchoServer {

    // 用于阻塞服务线程，因为是异步的，如果不阻塞服务将自动退出
    private CountDownLatch serverStatus = new CountDownLatch(1);

    public EchoServer(int port, int initPoolSize) {
        // 创建用于监听系统IO事件完成的回调处理方法的线程池
        // 此处使用缓存线程池，根据需要自己创建多少个线程
        //池中的每个线程都在等待IO事件，当IO操作完成后，触发相应的IO时间，调用池中的线程IO回调函数（CompleteHandler）
        ExecutorService executorService = Executors.newCachedThreadPool();
        try {
            AsynchronousChannelGroup asyncChannelGroup =
                AsynchronousChannelGroup.withCachedThreadPool(executorService, initPoolSize);
            AsynchronousServerSocketChannel asyncServerChannel =
                AsynchronousServerSocketChannel.open(asyncChannelGroup);
            asyncServerChannel.bind(new InetSocketAddress(port));
            System.out.println("Server started with listen on port: " + port);
            asyncServerChannel.accept(asyncServerChannel, new ConnectCompletionHandler());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        EchoServer server = new EchoServer(8888, 5);
        // 阻塞服务主线程
        try {
            server.serverStatus.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
