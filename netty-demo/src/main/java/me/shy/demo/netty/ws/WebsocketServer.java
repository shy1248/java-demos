package me.shy.demo.netty.ws;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import java.nio.charset.Charset;
import java.time.LocalDateTime;

/**
 * @Since: 2020/3/18 21:14
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 *
 **/
public class WebsocketServer {
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private ServerBootstrap bootstrap;

    public static void main(String[] args) {
        WebsocketServer server = new WebsocketServer();
        ChannelFuture channelFuture = server.forever(9999);
        try {
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if(server != null){
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
                    // 添加WebSocket相关Handler
                    ch.pipeline().addLast(new HttpServerCodec());
                    ch.pipeline().addLast(new ChunkedWriteHandler(1024));
                    ch.pipeline().addLast(new HttpObjectAggregator(1024 * 8));
                    ch.pipeline().addLast(new WebSocketServerProtocolHandler("/ws"));
                    // 添加业务逻辑Handler
                    ch.pipeline().addLast(new EchoHandler());

                }
            });

        ChannelFuture channelFuture = null;
        try {
            channelFuture = bootstrap.bind(port).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return channelFuture;
    }

    public void release(){
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

    class EchoHandler extends ChannelInboundHandlerAdapter{
        @Override public void channelActive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("Client: " + ctx.channel().remoteAddress() + " has been connected!");
        }

        @Override public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("Client: " + ctx.channel().remoteAddress() + " has been disconnected!");
        }

        @Override public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            // 处理websocket信息
            if(msg instanceof TextWebSocketFrame){
                TextWebSocketFrame frame = (TextWebSocketFrame)msg;
                String message = frame.text();
                System.out.println("Recevied from websocket client: " + message);
                TextWebSocketFrame response = new TextWebSocketFrame(LocalDateTime.now() + ": " + message);
                ctx.channel().writeAndFlush(response);
            }
        }

        @Override public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            System.out.println("An error occourd: " + cause.getMessage());
            cause.printStackTrace();
            ctx.close();
        }
    }
}
