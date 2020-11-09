/**
 * @Since: 2020-01-06 21:21:19
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 */
package me.shy.demo.netty.echoServer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: 基于 Netty 实现的 EchoServer
 *
 * Netty 实现 TCP 通信的一般步骤：
 * 1.创建2个 nio 线程组，一个用于处理网络事件（接受客户端连接），一个用于网络通信（数据读写）；
 * 2.创建一个 ServerBootstrap对象，配置一系列参数，如接受传出数据的缓冲区大小等；
 * 3.创建一个实际处理数据类ChannleInitilizer，进行初始化准备工作，比如设置接受传出数据的字符集、格式 以及实际处理数据的接口；
 * 4.绑定端口，执行同步阻塞方法等待服务器启动即可。

 * Netty线程模型： 构建线程组对象时如果指定线程个数，则默认为CPU的核心数； 1.单线程模型（个人机开发测试）；
 * 单线程模型的设置方法为：初始化线程组时设置线程数量为1，且构建ServerBootstrap绑定线程组时绑定父线程组和子线程组为同一个。
 * 2.多线程模型（长连接，连接数量较少）；
 *   多线程模型的设置方法为：初始化主线程组时设置线程数量为1，初始化子线程组时设置线程数大于1，且构建ServerBootstrap绑定线程组时绑定父线程组和子线程组不为同一个。
 * 3.主从多线程模型（长连接，连接数量较多）；
 * 主多线程模型的设置方法为：初始化主线程组和子线程组时设置线程数大于1，且构建ServerBootstrap绑定线程组时绑定父线程组和子线程组不为同一个。
 * 以上模型针对的是服务器。
 *
 * 拆包和粘包的问题：
 * Netty使用的是TCP/IP协议传输数据，基于Socket流一样，而Netty使用Nio模型时，可能导致多个数据包合在一起，或者一个数据包被拆成了多个，这种现象就叫粘包和拆包。
 * 处理办法一般有：
 * 1.定长数据流。客户端与服务器端约定好每个消息的长度，如固定为10，不足10位则在后边补充空白字符。
 *   Netty官方提供了2个Handler来帮助我们：
 *   io.netty.handler.codec.FixedLengthFrameDecoder(LENGTH)
 *   io.netty.handler.codec.StringDecoder(Charset.forName("utf-8"))
 * 2.使用特殊的消息结束符。如每条消息的结尾都是用$_$_$，这样在通信的时候，只要没有发送分隔符号，则代表消息未发送完毕，必须等待，消息长度可以不固定。
 * 3.使用协议。使用固定的协议进行发送和解析。
 *
 * 序列化与压缩 定时断线重连
 * 1.当客户端数量较多，且需要传递较大的数据量，对数据传输的即时性要求不高时，可与使用Netty的短线重连机制；
 * 2.每次短线重连都是由客户端发起的；
 * 3.可以使用Netty官方提供的ReadTimeoutHandler与WriteTimeoutHandler来实现；
 * 4.使用断线重连可以重用缓存数据减轻服务器压力。比如客户端可以制定一种策略：每120S断开重连一次，每累计1000条消息发送一次，300s无论是否有1000条消息都发送；
 *
 * 心跳检测
 *
 * 1.一个EventLoopGroup当中会包含一个或者多个EventLoop；
 * 2.一个EventLoop在整个生命周期中只会与唯一一个Thread进行绑定；
 * 3.所有由EventLoop所处理的各种IO事件都将在它所关联的那个Threads上进行处理，不会产生线程安全问题；
 * 4.一个Channel在它整个生命周期内只会注册在一个EventLoop上；
 * 5.一个EventLoop在运行过程中，会分配给一个或者多个Channle；
 *
 * 在Netty中，Channel的实现一定是线程安全的；基于此，我们可以存储一个Channel的引用，并在需要向y远程断点发送数据时，通过这个引用来调用Channel的相关方法
 * 来实现；即便有很多线程来同时调用t它也不会出现多线程的安全问题，而且消息一定会按照顺序发送出去。
 *
 * 绝对不要在业务Handler中做耗时的操作，这种耗时的操作会阻塞该EventLoop上所有相关Channel的IO操作，而是应该另起EventExecutor线程操作；
 * 通常有2中方法：
 * 1）使用Java提供的异步线程池：ExecutorService.submit()来提交异步耗时操作到单独的线程；
 * 2）在给Netty添加Handler时，使用重载的方法：pipeLine.addLast(EventEcecutorGroup, name, handler)，其中，第一个参数EventExecutorGroup就表示该
 * Handler在单独的线程组中执行，不会阻塞IO线程；
 * 说明：默认情况下，Handler中的操作都是由IO线程执行的，除非在添加Handler时指定了EventEcecutorGroup，则是在EventEcecutorGroup线程组中执行。
 *
 * Netty对JDK的Future进行了增强，可通过添加监听器以回调方式处理Future的结果。同样的，ChannleFutureListener的operationComplete方法是由IO线程执行的，
 * 因此该方法里边如果有耗时操作，需要另起线程处理。
 *
 * Channel的wrtieAndFlush与ChannelHandlerContext的wrtieAndFlush方法区别：
 * Channel的写方法会从出站的最底层的Handler开始，经过所有的Handler处理才到达对端；
 * 而ChannelHandlerContext的写方法则是从出站方向的当前Handler的下一个Handler开始，直到到达对端；
 * 总结的话，就是ChannelHandlerContext作用域为下一个Handler开始，而Channle的作用则是所有的Handler。
 * 所以：
 * 1）ChannelHandlerContext与ChannelHandler的绑定关系是永远不会改变，因此对其缓存没有任何问题；
 * 2）对于Channel与ChannelHandlerContext的同名方法，ChannelHandlerContext会有更短的事件流，效率更高，如果没有必要，尽量使用ChannelHandlerContext中的方法；
 *
 * Netty同时支持传统的IO，即Oio开头的类，使用比较少，编程模型与Nio相似。
 *
 * 对于同一个服务，如果既是服务端又是客户端，那么最佳实践是服务端与客户端共用一个EventLoop。
 *      public void channelActive(ChannelHandlerContext ctx) {
 *          BootStrap bootstrap = ...
 *          bootrap.cahnnel(NioSocketChannel.class).handler(
 *              ...
 *              bootstrap.group(ctx.channel().eventLoop());
 *              bootstrap.connect()...
 *          );
 *      }
 *
 *
 *  Netty处理器的重要概念：
 *  1.Netty的处理器分为2大类：入站处理器和出站处理器；
 *  2.入站处理器的顶层是ChannleInboundHandler，出站处理器的顶层是ChannelOutboundHandler；
 *  3.数据处理时常用的各种编解码器都是处理器；
 *  4.编解码器：无论我们向网络写入什么样的（int，char，String，byte等），数据在网络的传输过程中，都是以字节流的形式呈现的。
 *    将数据由原本的格式转换为字节流的操作称为编码（encode），将字节流转换为它原本的格式的操作称为解码（decode）；
 *  5.编码本质上是一种出战处理器，因此编码一定是ChannelOutboundHandler；
 *  6.解码本质上是一种入站处理器，因此解码一定是ChannelInboundHandler；
 *
 */
public class EchoServer {

    // 创建父线程组，用于接受客户端请求
    private EventLoopGroup childGroup;
    // 创建子线程组，用于处理客户请求
    private EventLoopGroup parentGroup;
    private ServerBootstrap serverBootstrap;

    public static void main(String[] args) {
        EchoServer server = new EchoServer();
        server.init();
        server.doAccept(9999, server.new EchoServerHandler());
    }

    private void release() {
        // 释放线程资源组
        parentGroup.shutdownGracefully();
        childGroup.shutdownGracefully();
    }

    private void init() {
        // 创建监听客户端连接的线程组
        parentGroup = new NioEventLoopGroup(1);
        // 创建用于已建立连接的网络通信（读写数据）的线程组
        childGroup = new NioEventLoopGroup(1);
        // 创建 netty 中代表 TCPServer 的引擎类
        serverBootstrap = new ServerBootstrap();
        // 设置一系列的参数
        serverBootstrap.group(parentGroup, childGroup)
                // 指定使用 nio 模式
                .channel(NioServerSocketChannel.class)
                // 设置服务器用于等待连接的队列大小
                .option(ChannelOption.SO_BACKLOG, 128)
                // 设置接收消息的缓冲区大小，单位为字节
                .option(ChannelOption.SO_RCVBUF, 4 * 1024);
        // 设置发送消息的缓冲区大小
        // .option(ChannelOption.SO_SNDBUF, 4 * 1024)
        // 设置长连接支持
        // .option(ChannelOption.SO_KEEPALIVE, true)
        // 禁用 TCP 延迟确认
        // .option(ChannelOption.TCP_NODELAY, true);
    }

    private ChannelFuture doAccept(int port, final ChannelHandler... handlers) {
        // 添加处理网络请求的回调类
        serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                // 这里设置实际处理数据的回调类
                ch.pipeline().addLast(handlers);
            }
        });
        // 进行端口绑定，并等待绑定操作完成，可以绑定多个端口，同时客户端也能连接多个端口
        ChannelFuture channelFuture = null;
        try {
            channelFuture = serverBootstrap.bind(port).sync();
            System.out.println("Server startup on: " + port);
            // 等待程序结束事件完成
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return channelFuture;
    }

    // @Sharable表示Channel可以多个客户端重用，如果没有这个注解，其它客户端在前一个客户端断开后
    // Channel也随之关闭，必须重建Channel才行
    @Sharable
    public class EchoServerHandler extends ChannelInboundHandlerAdapter {

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            ByteBuf buf = (ByteBuf) msg;
            byte[] bytes = new byte[buf.readableBytes()];
            buf.readBytes(bytes);
            String data = new String(bytes, StandardCharsets.UTF_8);
            System.out.println("Client message is: " + data);
            if ("exit".equals(data.toLowerCase())) {
                ctx.close();
                return;
            }
            ctx.channel().writeAndFlush(Unpooled.copiedBuffer(data.getBytes(StandardCharsets.UTF_8)));
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            System.out.println("An error caught on server: " + cause.getMessage());
            cause.printStackTrace();
            ctx.close();
        }
    }

}
