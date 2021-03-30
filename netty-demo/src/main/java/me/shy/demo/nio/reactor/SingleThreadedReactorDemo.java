package me.shy.demo.nio.reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Set;

/**
 * @Since: 2020/3/25 11:31
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 *
 **/
public class SingleThreadedReactorDemo implements Runnable {
    final SelectorProvider provider = SelectorProvider.provider();
    Selector selector = null;
    ServerSocketChannel serverSocketChannel = null;

    public SingleThreadedReactorDemo(int port) {
        try {
            // 创建多路复用器
            // selector = Selector.open();
            // serverSocketChannel = ServerSocketChannel.open();
            selector = provider.openSelector();
            // 创建 ServerSocketChannle
            serverSocketChannel = provider.openServerSocketChannel();
            // ServerSocketChannle 绑定监听端口
            serverSocketChannel.socket().bind(new InetSocketAddress(port));
            // ServerSocketChannle 配置为非阻塞状态
            serverSocketChannel.configureBlocking(false);
            // ServerSocketChannle 声明感兴趣的事件为 OP_ACCEPT
            // 当服务器启动时，第一感兴趣的事件永远为 OP_ACCEPT，即可接受客户端连接
            SelectionKey sk = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            // 为事件 OP_ACCEPT 注册 Handler
            sk.attach(new Acceptor());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SingleThreadedReactorDemo reactorDemo = new SingleThreadedReactorDemo(9999);
        System.out.println("Server started, listen on port 9999 ...");
        reactorDemo.run();
    }

    @Override public void run() {
        while (!Thread.interrupted()) {
            try {
                // 此方法阻塞，直到至少有一个IO事件发生才返回
                // 由于事先声明感兴趣的事件只有 OP_ACCEPT，因此这里只会处理客户端连接事件
                selector.select();
                // 上面代码返回了，说明至少有一个IO事件产生了，此时获取所有事件的集合
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                // 获取IO事件集合的迭代器
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                // 对IO事件进行迭代处理，此处分发事件
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    if(!selectionKey.isValid()){
                        continue;
                    }
                    dispatch(selectionKey);
                }
                // 所有的事件分发完毕后，必须要清空事件集合，否则会导致重复处理
                selectionKeys.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void dispatch(SelectionKey sk) {
        // 由于之前绑定的 Acceptor 对象实现了 Runnable 接口，此处直接强转
        Runnable r = (Runnable)sk.attachment();
        if (null != r) {
            r.run();
        }
    }

    class Acceptor implements Runnable {
        String welcome = "Welcom to this server, try to type everything ...\n";
        @Override public void run() {
            try {
                // Acceptor 只负责接受客户端请求
                // 一个 SocketChannle 即表示与客户端建立好的连接通道
                SocketChannel socketChannel = serverSocketChannel.accept();
                if (null != socketChannel) {
                    System.out.println("Client[" + socketChannel.socket().getRemoteSocketAddress() + "] is connected.");
                    ByteBuffer banner = ByteBuffer.wrap(welcome.getBytes(StandardCharsets.UTF_8));
                    socketChannel.write(banner);
                    new ReadHandler(selector, socketChannel);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class ReadHandler implements Runnable {
        SocketChannel socketChannel = null;
        SelectionKey selectionKey = null;
        StringBuffer messageBuffer = new StringBuffer();

        public ReadHandler(Selector selector, SocketChannel socketChannel) {
            this.socketChannel = socketChannel;
            // 将客户端通道设置为非阻塞
            try {
                this.socketChannel.configureBlocking(false);
                // 客户端通道声明感兴趣的事件为 OP_READ，即准备从客户端读取请求内容
                selectionKey = socketChannel.register(selector, SelectionKey.OP_READ, this);
                // 唤醒多路复用器，意思是让多路复用器在侦听事件时不阻塞
                selector.wakeup();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override public void run() {
            try {
                ByteBuffer input = ByteBuffer.allocateDirect(1024 * 8);
                socketChannel.read(input);
                input.flip();
                byte[] readBytes = new byte[input.remaining()];
                input.get(readBytes);
                String chunk = new String(readBytes, StandardCharsets.UTF_8);
                messageBuffer.append(chunk);
                System.out.println("Request: " + messageBuffer);
                new WriteHandler(selectionKey.selector(), socketChannel, messageBuffer.toString());
            } catch (IOException e) {
                selectionKey.cancel();
                e.printStackTrace();
            }
        }
    }

    class WriteHandler implements Runnable {
        Selector selector = null;
        SocketChannel socketChannel = null;
        String message = null;

        public WriteHandler(Selector selector, SocketChannel socketChannel, String message) {
            this.selector = selector;
            this.socketChannel = socketChannel;
            this.message = message;
            try {
                socketChannel.register(this.selector, SelectionKey.OP_WRITE, this);
                this.selector.wakeup();
            } catch (ClosedChannelException e) {
                e.printStackTrace();
            }
        }

        @Override public void run() {
            try {
                byte[] response = ("[" + LocalDateTime.now()  + "]: " + message).getBytes(StandardCharsets.UTF_8);
                ByteBuffer output = ByteBuffer.allocateDirect(response.length);
                output.put(response);
                output.flip();

                socketChannel.write(output);
                System.out.println("Response: " + new String(response, StandardCharsets.UTF_8));
                new ReadHandler(selector, socketChannel);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
