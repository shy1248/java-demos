package me.shy.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: nio, NonBlockingIO，非阻塞、同步IO，实现 EchoServer
 *
 * Java.io中的核心概念是流(stream)，所有的io操作都是基于流的。一个流要么是输入流，要么是输出流，不可能同时是输入流又是输出流；
 * Java.nio中则有三个概念：多路复用器(Selector)，通道(Channle)和缓冲区(Buffer)，nio主要面向块(Block)或者缓冲区(Buffer)的。
 * Buffer本身就是一块内存，底层使用数组来实现的。数据的读与写都是通过Buffer来进行的，而不会直接通过Channel来进行读写。
 *
 * Channel本身是双向的。即可以写，也能读。
 *
 * 使用 Selector 多路复用器（基于epoll）进行事件轮询
 * serverSocketChannel.configureBlocking(false) 设置为非阻塞IO
 * 然后 Selector 从事件队列中挑选已就绪的通道，对通道类型进行判断，调用对应的方法进行处理。
 *
 * 需要不停的轮询事件，开销较大。
 */
public class EchoServer implements Runnable {

    static final int PORT = 8888;
    // 创建保存客户端连接的缓存
    ConcurrentHashMap<SelectionKey, ByteBuffer> eventCache = new ConcurrentHashMap<SelectionKey, ByteBuffer>();

    // 创建多路复用器，用于管理所有的通道
    private Selector selector;

    public EchoServer(int port) {
        try {
            // 打开多路复用器
            this.selector = Selector.open();
            // 打开服务通道
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            // 绑定服务通道的端口
            serverSocketChannel.bind(new InetSocketAddress(port));
            // 将服务通道设置为非阻塞状态
            serverSocketChannel.configureBlocking(false);
            // 把服务通道注册到多路复用器上，并监听阻塞事件
            serverSocketChannel.register(this.selector, SelectionKey.OP_ACCEPT);
            System.out.println("Server is started, listen on port: " + port);
        } catch (IOException e) {
            System.out.println(" An error occourd during initilize server: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Thread(new EchoServer(PORT)).start();
    }

    // 处理可写事件
    // 如果是可写事件，表示可以响应给客户端了，就要写出内容，然后将通道注册为可读状态
    private void write(SelectionKey event) {
        // 获取客户端通道，并写入响应消息给客户端
        SocketChannel socketChannel = (SocketChannel)event.channel();
        // 获取 Buffer
        ByteBuffer buffer = this.getBuffer(event);
        // 从 Buffer 构造需要响应给客户端的内容
        String resp = this.readBuffer(buffer, false);
        // 清空缓冲区
        buffer.clear();
        // 将要响应给客户端的内容转为字节数组并写入写缓冲区
        buffer.put(resp.getBytes());
        buffer.flip();

        try {
            socketChannel.write(buffer);
        } catch (IOException e) {
            System.out.println("Client is closed!");
            event.cancel();
            return;
        }

        // 将客户端的通道设置为可读状态
        this.register(socketChannel, SelectionKey.OP_READ);
    }

    // 处理可读事件
    // 如果是可读事件，表示客户端端发送了消息过来，就要读取内容，然后将通道注册为可写状态
    private void read(SelectionKey event) {
        // 获取客户端通道
        SocketChannel socketChannel = (SocketChannel)event.channel();
        // 清空读缓冲区，避免读取到其它内容
        ByteBuffer buffer = this.getBuffer(event);
        buffer.clear();

        try {
            // 读取客户端发送的内容到读 buffer 中
            socketChannel.read(buffer);
        } catch (IOException e) {
            System.out.println("Client is closed!");
            event.cancel();
            return;
        }

        String resp = this.readBuffer(buffer, true);
        System.out.println(resp);
        // 将客户端通道设置为可写状态
        this.register(socketChannel, SelectionKey.OP_WRITE);
    }

    // 处理 accept 事件
    // 如果是阻塞事件 accept，表示客户端已经连接过来了，就要接受连接，并将通道注册为可读状态
    private void accept(SelectionKey event) {
        // 获取事件的服务通道
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel)event.channel();
        SocketChannel socketChannel = null;

        try {
            // 接受客户端的连接
            socketChannel = serverSocketChannel.accept();
            // 将服务通道设置为非阻塞状态
            socketChannel.configureBlocking(false);
        } catch (IOException e) {
            System.out.println("Client is closed!");
            event.cancel();
            return;
        }

        System.out.println("A connection accept from " + socketChannel.socket().getLocalSocketAddress());
        // 将通道注册到多路复用器上，并将客户端的通道设置为可读状态（因为已经接受了）
        this.register(socketChannel, SelectionKey.OP_READ);
    }

    private void register(SocketChannel socketChannel, int state) {
        try {
            socketChannel.register(this.selector, state);
        } catch (ClosedChannelException e) {
            System.out.println(" An error occourd during register to " + state + " state: " + e.getMessage());
        }
    }

    // 从 buffer 中读取内容，并封装为响应内容
    private String readBuffer(ByteBuffer buffer, boolean isRecv) {
        // 如果有消息，使用字节数据接受缓冲区的内容，接受之前需要先将读缓冲区复位，否则读到的内容可能不完整
        buffer.flip();
        // 创建字节数组来接受都缓冲区的内容
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);
        // 将字节数组转为字符串
        String msg = new String(bytes).trim();
        if (isRecv) {
            System.out.println("Server recevied: " + msg);
        }
        // 格式化当前时间字符串
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd HH:MM:SS"));
        // 构造响应内容
        String resp = "[Server - " + now + "]:  " + msg;
        return resp;
    }

    // 为每个 event 事件缓存其 buffer 对象，每个连接有一个单独的 buffer
    // 方便 event 可读或者可写时获取对应的值
    private ByteBuffer getBuffer(SelectionKey enevt) {
        ByteBuffer buffer = this.eventCache.get(enevt);
        if (null == buffer) {
            buffer = ByteBuffer.allocate(1024);
            eventCache.put(enevt, buffer);
        }
        return buffer;
    }

    @Override public void run() {
        while (true) {
            try {
                // 多路复用器启动监听
                this.selector.select();
            } catch (IOException e) {
                System.out.println(" An error occourd during select events: " + e.getMessage());
            }

            // 多路复用器开始轮询事件
            Iterator<SelectionKey> events = this.selector.selectedKeys().iterator();
            while (events.hasNext()) {
                // 取出事件并将事件从事件队列中移除
                SelectionKey event = events.next();
                events.remove();

                // 如果是无效事件，跳过本次轮询
                // 以下任何一个状态在进行读写操作时发生 IOException 表示连接断开，将事件 cancle 变为无效事件
                if (!event.isValid()) {
                    continue;
                }
                // 如果是阻塞事件 accept，表示客户端已经连接过来了，就要接受连接，并将通道注册为可读状态
                else if (event.isAcceptable()) {
                    this.accept(event);
                }
                // 如果是可读事件，表示客户端端发送了消息过来，就要读取内容，然后将通道注册为可写状态
                else if (event.isReadable()) {
                    this.read(event);
                }
                // 如果是可写事件，表示可以响应给客户端了，就要写出内容，然后将通道注册为可读状态
                else if (event.isWritable()) {
                    this.write(event);
                }
            }
        }
    }

}
