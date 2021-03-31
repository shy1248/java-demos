package me.shy.netty.netty.stickingAndUnpacking;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 */
public class TestServer {
    public static void main(String[] args) throws InterruptedException {
        TestServer ts = new TestServer();
        // ts.forever(asFixedLength());
        // ts.forever(asDelimiterBased());
        ts.forever(asProtocol());
    }

    private static ChannelHandler[] asProtocol() {
        // 创建handler数组
        ChannelHandler[] handlers = new ChannelHandler[3];
        // 第二个为字符解码的handler，会自动将收到的消息根据指定的字符集解码成字符串
        // 这样后面的handler就可以直接处理字符串了
        handlers[0] = new StringDecoder(StandardCharsets.UTF_8);
        handlers[1] = new TestServer().new SimpleStringProtoHandler();
        // 自定义的handler，主要用来打印测试，保持消息结束符为_$_
        handlers[2] = new TestServer().new PrintHandler(SimpleMessageProto.warp("OK"));
        return handlers;
    }

    private static ChannelHandler[] asDelimiterBased() {
        String delimiter = "_$_";
        // 创建handler数组
        ChannelHandler[] handlers = new ChannelHandler[3];
        // 定义消息结束符，此处为_$_，并转换为ByteBuf类型
        ByteBuf delimiterByteBuf = Unpooled.copiedBuffer(delimiter.getBytes(StandardCharsets.UTF_8));
        // 定义基于结束符的handler，第一个参数为数据的最大长度，第二个为消息结束符
        // 如果消息超过最大长度任然没有结束，则会抛异常
        handlers[0] = new DelimiterBasedFrameDecoder(15, delimiterByteBuf);
        // 第二个为字符解码的handler，会自动将收到的消息根据指定的字符集解码成字符串
        // 这样后面的handler就可以直接处理字符串了
        handlers[1] = new StringDecoder(StandardCharsets.UTF_8);
        // 自定义的handler，主要用来打印测试，保持消息结束符为_$_
        handlers[2] = new TestServer().new PrintHandler("OK" + delimiter);
        return handlers;
    }

    private static ChannelHandler[] asFixedLength() {
        int messageLength = 3;
        // 创建handler数组
        ChannelHandler[] handlers = new ChannelHandler[3];
        // 第一个为固定长度的字节解码的handler，参数3表示每条消息的长度为3个字节，
        // 不足3个字节的等待，超过3个字节的会按照3个字节来进行拆分成多条消息
        handlers[0] = new FixedLengthFrameDecoder(messageLength);
        // 第二个为字符解码的handler，会自动将收到的消息根据指定的字符集解码成字符串
        // 这样后面的handler就可以直接处理字符串了
        handlers[1] = new StringDecoder(StandardCharsets.UTF_8);
        // 自定义的handler，主要用来打印测试，由于消息固定长度定义为3，此处必须也为3个字节
        handlers[2] = new TestServer().new PrintHandler("OK ");
        return handlers;
    }

    private void forever(ChannelHandler[] handlers) throws InterruptedException {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup(1);
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(eventLoopGroup, eventLoopGroup).channel(NioServerSocketChannel.class)
            .option(ChannelOption.SO_BACKLOG, 128).option(ChannelOption.SO_RCVBUF, 4 * 1024)
            .childHandler(new ChannelInitializer<SocketChannel>() {

                @Override protected void initChannel(SocketChannel ch) throws Exception {

                    ch.pipeline().addLast(handlers);
                }
            });

        ChannelFuture channelFuture = bootstrap.bind(9999).sync();
        System.out.println("Server startup on 9999 ...");
        channelFuture.channel().closeFuture().sync();
    }

    class SimpleStringProtoHandler extends ChannelInboundHandlerAdapter {
        @Override public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            System.out.println("Recevied from client protocol string: " + (String)msg);
            String message = SimpleMessageProto.parse((String)msg);
            super.channelRead(ctx, message);
        }
    }

    class PrintHandler extends ChannelInboundHandlerAdapter {
        public String replayedMessage;

        public PrintHandler(String replayedMessage) {
            this.replayedMessage = replayedMessage;
        }

        @Override public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            // 由于加了StringDecoder handler，因此这里可以直接处理字符串
            // 否则此处为ByteBuf对象
            String message = (String)msg;
            System.out.println("Recevied from client message: " + message);
            ctx.writeAndFlush(Unpooled.copiedBuffer(this.replayedMessage.getBytes(Charset.forName("utf-8"))));
        }

        @Override public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
        }
    }
}
