package me.shy.netty.netty.stickingAndUnpacking;

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
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import java.nio.charset.Charset;
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
public class TestClient {
    private NioEventLoopGroup eventLoopGroup;
    private Bootstrap bootstrap;

    public static void main(String[] args) throws InterruptedException {
        TestClient tc = new TestClient();
        // ChannelFuture channelFuture = tc.doRequest("127.0.0.1", 9999, testForFixedLength());
        // ChannelFuture channelFuture = tc.doRequest("127.0.0.1", 9999, testForDelimiterBased());
        ChannelFuture channelFuture = tc.doRequest("127.0.0.1", 9999, testForProtocol());

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Input the message to server > ");
            if (scanner.hasNextLine()) {
                // String message = scanner.nextLine();
                String message = SimpleMessageProto.warp(scanner.nextLine());
                channelFuture.channel().writeAndFlush(Unpooled.copiedBuffer(message.getBytes(StandardCharsets.UTF_8)));
                TimeUnit.MILLISECONDS.sleep(100);
            }
        }
    }

    private static ChannelHandler[] testForProtocol() {
        ChannelHandler[] handlers = new ChannelHandler[3];
        handlers[0] = new StringDecoder(Charset.forName("utf-8"));
        handlers[1] = new TestClient().new SimpleStringProtoHandler();
        handlers[2] = new TestClient().new PrintHandler();
        return handlers;
    }

    private static ChannelHandler[] testForDelimiterBased() {
        ChannelHandler[] handlers = new ChannelHandler[3];
        ByteBuf delimiter = Unpooled.copiedBuffer("_$_".getBytes(StandardCharsets.UTF_8));
        handlers[0] = new DelimiterBasedFrameDecoder(15, delimiter);
        handlers[1] = new StringDecoder(Charset.forName("utf-8"));
        handlers[2] = new TestClient().new PrintHandler();
        return handlers;
    }

    private static ChannelHandler[] testForFixedLength() {
        ChannelHandler[] handlers = new ChannelHandler[3];
        handlers[0] = new FixedLengthFrameDecoder(3);
        handlers[1] = new StringDecoder(Charset.forName("utf-8"));
        handlers[2] = new TestClient().new PrintHandler();
        return handlers;
    }

    private ChannelFuture doRequest(String host, int port, ChannelHandler[] handlers) {
        eventLoopGroup = new NioEventLoopGroup(1);
        bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class);
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

    class SimpleStringProtoHandler extends ChannelInboundHandlerAdapter {
        @Override public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            System.out.println("Recevied from server protocol string: " + (String)msg);
            String message = SimpleMessageProto.parse((String)msg);
            super.channelRead(ctx, message);
        }
    }

    class PrintHandler extends ChannelInboundHandlerAdapter {
        @Override public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            System.out.println("Recevied from server: " + msg);
        }
    }
}
