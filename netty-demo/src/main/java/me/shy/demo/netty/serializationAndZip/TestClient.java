package me.shy.demo.netty.serializationAndZip;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.WriteTimeoutHandler;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Random;
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
    private ChannelFuture channelFuture;

    public static void main(String[] args) throws InterruptedException, IOException {
        TestClient tc = new TestClient();
        tc.init();

        for (int i = 0; i < 10; i++) {
            tc.channelFuture = tc.doRequest("127.0.0.1", 9999);
            MessageBean request =
                new MessageBean(new Random(System.currentTimeMillis()).nextLong(), "Hello, " + "Server",
                    GzipUtil.zip("This is a test message!".getBytes(StandardCharsets.UTF_8)));
            tc.channelFuture.channel().writeAndFlush(request);
            TimeUnit.SECONDS.sleep(new Random(System.currentTimeMillis()).nextInt(10));
        }

        TimeUnit.SECONDS.sleep(1);
        tc.eventLoopGroup.shutdownGracefully();

    }

    private void init() {
        eventLoopGroup = new NioEventLoopGroup(1);
        bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new WriteTimeoutHandler(5));
                ch.pipeline().addLast(new KryoEncoder());
                ch.pipeline().addLast(new KryoDecoder());
                ch.pipeline().addLast(new PrintHandler());
            }
        });
    }

    private ChannelFuture doRequest(String host, int port) throws InterruptedException {
        if (null == channelFuture || !channelFuture.channel().isActive()) {
            channelFuture = bootstrap.connect(host, port).sync();
        }
        return channelFuture;
    }

    private void release() {
        this.eventLoopGroup.shutdownGracefully();
    }

    class PrintHandler extends ChannelInboundHandlerAdapter {
        @Override public void channelActive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("Connect to server 127.0.0.1:9999...");
        }

        @Override public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            if (null != msg && msg instanceof MessageBean) {
                MessageBean bean = (MessageBean)msg;
                System.out.println("Recevied from server: " + bean);
                System.out.println(
                    "Attachement is: " + new String(GzipUtil.unzip(bean.getAttachement()), StandardCharsets.UTF_8));
            }
        }

        @Override public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            System.out.println("Client: an error causghted: " + cause.getMessage());
        }
    }
}
