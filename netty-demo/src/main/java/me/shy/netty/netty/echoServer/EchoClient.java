package me.shy.netty.netty.echoServer;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 */
public class EchoClient {
    private NioEventLoopGroup eventLoopGroup;
    private Bootstrap bootstrap;

    public static void main(String[] args) throws InterruptedException {
        EchoClient c = new EchoClient();
        c.init();
        ChannelFuture channelFuture = c.doRequest("127.0.0.1", 9999, c.new ClientHandler());
        Scanner scanner = new Scanner(System.in);
        boolean isExit = false;

        while (!isExit) {
            System.out.print("Input the message (type \"exit\" to exit) > ");
            if (scanner.hasNextLine()) {
                String message = scanner.nextLine();
                if ("exit".equals(message.toLowerCase())) {
                    isExit = true;
                }
                channelFuture.channel().writeAndFlush(Unpooled.copiedBuffer(message.getBytes(StandardCharsets.UTF_8)));

                TimeUnit.MILLISECONDS.sleep(100);
            }
        }

        System.out.println("Exit after 1 sec...");
        c.release();

    }

    private void init() {
        eventLoopGroup = new NioEventLoopGroup(1);
        bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class);
    }

    private ChannelFuture doRequest(String host, int port, ChannelHandler... handlers) {
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(handlers);
            }
        });
        ChannelFuture channelFuture = null;
        try {
            channelFuture = bootstrap.connect(host, port).sync();
        } catch (InterruptedException e) {
            System.out.println("An error occourd during connect to " + host + ":" + port + ": " + e.getMessage());
        }
        return channelFuture;
    }

    private void release() {
        this.eventLoopGroup.shutdownGracefully();
    }

    class ClientHandler extends ChannelInboundHandlerAdapter {
        @Override public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            ByteBuf buf = (ByteBuf)msg;
            byte[] bytes = new byte[buf.readableBytes()];
            buf.readBytes(bytes);
            String readed = new String(bytes, StandardCharsets.UTF_8);
            System.out.println("Server echo: " + readed);
        }

        @Override public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            System.out.println("an error caught on client: " + cause.getMessage());
            cause.printStackTrace();
        }
    }
}


