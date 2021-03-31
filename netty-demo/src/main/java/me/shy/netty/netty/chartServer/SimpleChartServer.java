package me.shy.netty.netty.chartServer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultEventExecutor;
import io.netty.util.concurrent.EventExecutor;
import java.net.SocketAddress;
import java.nio.charset.Charset;
import java.time.LocalDateTime;

/**
 * @Since: 2020/3/18 21:13
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 *
 **/
public class SimpleChartServer {
    // 保存客户端的channel，以进行消息转发
    ChannelGroup channels = new DefaultChannelGroup("clients", new DefaultEventExecutor());
    EventLoopGroup bossGroup;
    EventLoopGroup workerGroup;
    ServerBootstrap bootstrap;

    public static void main(String[] args) {
        SimpleChartServer server = new SimpleChartServer();
        ChannelFuture channelFuture = server.forever(9999);

        try {
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (null != server){
                server.release();
            }
        }

    }

    public ChannelFuture forever(int port){
        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup(2);
        bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
            .channel(NioServerSocketChannel.class)
            .handler(new LoggingHandler(LogLevel.DEBUG))
            .childOption(ChannelOption.SO_RCVBUF, 1024*8)
            .childHandler(new ChannelInitializer<SocketChannel>() {
                @Override protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast("string", new StringDecoder(Charset.forName("utf-8")));
                    ch.pipeline().addLast("chart", new ChartHandler());
                }
            });

        ChannelFuture channleFuture = null;
        try {
             channleFuture = bootstrap.bind(port).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return channleFuture;
    }

    public void release(){
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

    class ChartHandler extends ChannelInboundHandlerAdapter{

        @Override public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
            SocketAddress socketAddress = ctx.channel().remoteAddress();
            String message = "Client[" + socketAddress + "] is Online. Total clients is: " + (channels.size() + 1);
            System.out.println(message);
            channels.writeAndFlush(Unpooled.copiedBuffer((message + "\n").getBytes(Charset.forName("utf-8"))));
            // 将客户端保存
            channels.add(ctx.channel());
            System.out.println("Client size is: " + channels.size());
        }

        @Override public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
            SocketAddress socketAddress = ctx.channel().remoteAddress();
            String message = "Client[" + socketAddress + "] is Offline. Total clients is: " + channels.size();
            System.out.println(message);
            channels.writeAndFlush(Unpooled.copiedBuffer((message + "\n").getBytes(Charset.forName("utf-8"))));
            // 移除客户端，非必须，ChannelGroup自动维护断线的channle
            // channels.remove(ctx.channel());
        }

        @Override public void channelActive(ChannelHandlerContext ctx) throws Exception {
            SocketAddress socketAddress = ctx.channel().remoteAddress();
            System.out.println("Client[" + socketAddress + "] has been connected!");
        }

        @Override public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            SocketAddress socketAddress = ctx.channel().remoteAddress();
            System.out.println("Client[" + socketAddress + "] has been disconnected!");
        }

        @Override public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            SocketAddress socketAddress = ctx.channel().remoteAddress();
            String perfix = "";
            for(Channel c: channels){
                if(c == ctx.channel()){
                    perfix = "SELF - ";
                }else{
                    perfix = "Client[" + socketAddress + "] - ";
                }
                String message = perfix + "[" + LocalDateTime.now() + "]: " + (String)msg;
                c.writeAndFlush(Unpooled.copiedBuffer((message + "\n").getBytes(Charset.forName("utf-8"))));
            }
        }

        @Override public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            System.out.println("An error occourd: " + cause.getMessage());
            cause.printStackTrace();
            ctx.close();
        }
    }
}
