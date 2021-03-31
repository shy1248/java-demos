package me.shy.netty.netty.rpc.protobuf;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import me.shy.netty.netty.rpc.protobuf.MultiMessageProto.Pet;

/**
 * @Since: 2020/3/19 9:18
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: RPC, Remote Procedure Call, 远程过程调用
 *
 * rmi：remote method invocation，远程方法调用，只适用于Java语言。
 *
 * RPC是跨语言、跨平台的远程调用，使用了序列化与反序列化技术将数据通过网络字节流的方式传递给远程方法，
 * 远程方法处理完毕后将结果传递回调用方。客户端叫stub，服务端成为skeleton。流行的实现有：Apache Thrift，Google ProtoBuf。
 *
 * RPC编程流程通常为：
 * 1.定义一个接口说明文件：描述了对象（结构体）、对象成员、接口方法等一系列信息；
 * 2.通过RPC框架所提供的编译器，将接口说明文件编译成具体的语言文件；
 * 3.在客户端与服务器端分别引入RPCb编译器所生成的文件，即可像调用本地方法一样调用远程方法；
 *
 **/
public class ProtoBufDemoServer {
    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(2);
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
            .channel(NioServerSocketChannel.class)
            .handler(new LoggingHandler(LogLevel.DEBUG))
            .childOption(ChannelOption.SO_RCVBUF, 1024 * 8)
            .childHandler(new ChannelInitializer<SocketChannel>() {
                @Override protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    // 添加 protobuf 处理 handler
                    pipeline.addLast(new ProtobufVarint32FrameDecoder());
                    pipeline.addLast(new ProtobufDecoder(MultiMessageProto.Pet.getDefaultInstance()));
                    pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
                    pipeline.addLast(new ProtobufEncoder());
                    // 添加业务逻辑 handler
                    pipeline.addLast(new ProtoBufDemoServer().new PrinterHandler());
                }
            });
        try {
            ChannelFuture channleFuture = bootstrap.bind(9999).sync();
            channleFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    class PrinterHandler extends ChannelInboundHandlerAdapter{
        @Override public void channelActive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("Client[" + ctx.channel().remoteAddress() + "] has been connected!");
        }

        @Override public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("Client[" + ctx.channel().remoteAddress() + "] has been disconnected!");
        }

        @Override public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            if (msg instanceof MultiMessageProto.Pet){
                MultiMessageProto.Pet pet = (Pet)msg;
                // 根据 petType 来从 Pet 获取不同的 message
                switch (pet.getPetType()){
                    case DogType:
                        MultiMessageProto.Dog dog = pet.getDog();
                        System.out.println(dog);
                        break;
                    case CatType:
                        MultiMessageProto.Cat cat = pet.getCat();
                        System.out.println(cat);
                        break;
                }
            }
        }

        @Override public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            System.out.println("An error occourd: " + cause.getMessage());
            ctx.close();
        }
    }
}
