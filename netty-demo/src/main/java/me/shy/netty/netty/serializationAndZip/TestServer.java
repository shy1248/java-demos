package me.shy.netty.netty.serializationAndZip;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import java.net.SocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 */
public class TestServer {
    private NioEventLoopGroup eventLoopGroup;
    private ServerBootstrap bootstrap;

    public static void main(String[] args) throws InterruptedException {
        TestServer ts = new TestServer();
        ChannelFuture channelFuture = ts.forever(9999);
        if (channelFuture != null) {
            channelFuture.channel().closeFuture().sync();
        }
        if (ts != null) {
            ts.release();
        }
    }

    private ChannelFuture forever(int port) throws InterruptedException {
        eventLoopGroup = new NioEventLoopGroup(1);
        bootstrap = new ServerBootstrap();
        bootstrap.group(eventLoopGroup, eventLoopGroup).channel(NioServerSocketChannel.class)
            .option(ChannelOption.SO_BACKLOG, 128).option(ChannelOption.SO_RCVBUF, 4 * 1024)
            .handler(new LoggingHandler(LogLevel.DEBUG)).childHandler(new ChannelInitializer<SocketChannel>() {
            @Override protected void initChannel(SocketChannel ch) throws Exception {
                // 5s中没有读操作自动断线
                ch.pipeline().addLast(new ReadTimeoutHandler(5, TimeUnit.SECONDS));
                ch.pipeline().addLast(new KryoDecoder());
                ch.pipeline().addLast(new KryoEncoder());
                ch.pipeline().addLast(new PrintHandler());
            }
        });

        ChannelFuture channelFuture = bootstrap.bind(9999).sync();
        System.out.println("Server startup on 9999 ...");
        return channelFuture;
    }

    private void release() {
        this.eventLoopGroup.shutdownGracefully();
    }

    @Sharable class PrintHandler extends ChannelInboundHandlerAdapter {

        @Override public void channelActive(ChannelHandlerContext ctx) throws Exception {
            SocketAddress socketAddress = ctx.channel().remoteAddress();
            System.out.println("Client: " + socketAddress.toString() + " connected!");
        }

        @Override public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            System.out.println("An error caughted: " + cause.getMessage());
            ctx.close();
        }

        @Override public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            if (null != msg && msg instanceof MessageBean) {
                MessageBean bean = (MessageBean)msg;
                System.out.println("Recevied from client: " + bean);
                System.out.println(
                    "Attachement is: " + new String(GzipUtil.unzip(bean.getAttachement()), StandardCharsets.UTF_8));
                MessageBean resp = new MessageBean(10000, "OK",
                    GzipUtil.zip(("Your ID is: " + bean.getId()).getBytes(StandardCharsets.UTF_8)));
                ctx.writeAndFlush(resp);
            }
        }
    }
}
