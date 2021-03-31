package me.shy.netty.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
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
public class AsyncEchoClient {

    public final CountDownLatch latch = new CountDownLatch(1);
    public final Scanner scanner = new Scanner(System.in);
    public int port;
    public boolean isQuit = false;
    private AsynchronousSocketChannel asyncSocketChannel;

    public AsyncEchoClient(int port) {
        this.port = port;
        try {
            ExecutorService executorService = Executors.newFixedThreadPool(2);
            AsynchronousChannelGroup asyncChannelGroup = AsynchronousChannelGroup.withThreadPool(executorService);
            this.asyncSocketChannel = AsynchronousSocketChannel.open(asyncChannelGroup);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        AsyncEchoClient client = new AsyncEchoClient(8888);
        new Thread(() -> {
            client.connect("127.0.0.1");
        }).start();
        try {
            client.latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void connect(String host) {
        this.asyncSocketChannel.connect(new InetSocketAddress(host, this.port), this.asyncSocketChannel,
            new AsyncEchoClient.ConnectCompletionHandler());
    }

    public void disconnect() {
        try {
            this.scanner.close();
            this.asyncSocketChannel.shutdownInput();
            this.asyncSocketChannel.shutdownOutput();
            this.asyncSocketChannel.close();
            this.latch.countDown();
        } catch (IOException e) {
            System.out.println("An error occurd during closed client: " + e.getMessage());
        }
    }

    private String getInput() {
        String whatSay = null;
        if (!isQuit) {
            System.out.println("Enter you message:");
            // 当有输入时，获取输入内容
            if (this.scanner.hasNextLine()) {
                whatSay = scanner.nextLine();
            }
        }
        return whatSay;
    }

    class ConnectCompletionHandler implements CompletionHandler<Void, AsynchronousSocketChannel> {

        @lombok.SneakyThrows @Override public void completed(Void result, AsynchronousSocketChannel attachment) {
            System.out.println("Connect to server: 127.0.0.1:" + port + ".");
            String msg = getInput();
            ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
            writeBuffer.put(msg.getBytes(StandardCharsets.UTF_8));
            writeBuffer.flip();
            attachment.write(writeBuffer, writeBuffer, new WriteCompletionHandler(attachment));
        }

        @Override public void failed(Throwable exc, AsynchronousSocketChannel attachment) {
            System.out.println("An error occourd during connect to server: " + exc.getMessage());
        }
    }

    class ReadCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {

        private AsynchronousSocketChannel asyncSocketChannel;

        public ReadCompletionHandler(AsynchronousSocketChannel asyncSocketChannel) {
            this.asyncSocketChannel = asyncSocketChannel;

        }

        @Override public void completed(Integer result, ByteBuffer attachment) {
            System.out.println("Server response data length is: " + result);
            // 打印收到的消息至控制台
            attachment.flip();
            byte[] bytes = new byte[attachment.remaining()];
            attachment.get(bytes);
            System.out.println(new String(bytes, StandardCharsets.UTF_8));

            // 等待输入消息被那个发送
            String msg = null;
            while (null == (msg = getInput())) {
                msg = getInput();
            }

            if (msg.toUpperCase().equals(":QUIT") && !isQuit) {
                msg = "bye!";
                ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
                writeBuffer.put(msg.getBytes(StandardCharsets.UTF_8));
                writeBuffer.flip();
                this.asyncSocketChannel
                    .write(writeBuffer, writeBuffer, new WriteCompletionHandler(this.asyncSocketChannel));
                isQuit = true;
            }

            ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
            writeBuffer.put(msg.getBytes(StandardCharsets.UTF_8));
            writeBuffer.flip();
            this.asyncSocketChannel
                .write(writeBuffer, writeBuffer, new WriteCompletionHandler(this.asyncSocketChannel));

            if (isQuit) {
                disconnect();
            }
        }

        @Override public void failed(Throwable exc, ByteBuffer attachment) {
            System.out.println("An error occourd during recevied data: " + exc.getMessage());
        }
    }

    class WriteCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {

        private AsynchronousSocketChannel asyncSocketChannel;

        public WriteCompletionHandler(AsynchronousSocketChannel asyncSocketChannel) {
            this.asyncSocketChannel = asyncSocketChannel;
        }

        @Override public void completed(Integer result, ByteBuffer attachment) {
            ByteBuffer readBuffer = ByteBuffer.allocate(1024);
            this.asyncSocketChannel.read(readBuffer, readBuffer, new ReadCompletionHandler(this.asyncSocketChannel));
        }

        @Override public void failed(Throwable exc, ByteBuffer attachment) {
            System.out.println("An error occourd during send data: " + exc.getMessage());
        }
    }
}


