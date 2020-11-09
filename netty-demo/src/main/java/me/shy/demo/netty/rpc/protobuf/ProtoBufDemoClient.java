package me.shy.demo.netty.rpc.protobuf;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import me.shy.demo.netty.rpc.protobuf.MultiMessageProto.Cat;
import me.shy.demo.netty.rpc.protobuf.MultiMessageProto.Dog;
import me.shy.demo.netty.rpc.protobuf.MultiMessageProto.Master;
import me.shy.demo.netty.rpc.protobuf.MultiMessageProto.Pet;
import me.shy.demo.netty.rpc.protobuf.MultiMessageProto.Pet.PetType;

/**
 * @Since: 2020/3/19 21:36
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 *
 **/
public class ProtoBufDemoClient {
    public static void main(String[] args) {
        EventLoopGroup loopGroup = new NioEventLoopGroup(1);
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(loopGroup).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
            @Override protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                // 添加 protobuf 处理 handler
                pipeline.addLast(new ProtobufVarint32FrameDecoder());
                pipeline.addLast(new ProtobufDecoder(MultiMessageProto.Pet.getDefaultInstance()));
                pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
                pipeline.addLast(new ProtobufEncoder());
                // 添加业务逻辑 handler
                pipeline.addLast(new ProtoBufDemoClient().new RandomSendHandler());
            }
        });
        try {
            ChannelFuture channelFuture = bootstrap.connect("localhost", 9999).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            loopGroup.shutdownGracefully();
        }
    }

    class RandomSendHandler extends ChannelInboundHandlerAdapter {
        @Override public void channelActive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("Connect to server loaclhost:9999 ...");
            for (int i = 0; i < 10; i++) {
                Pet pet = null;
                int petType = new Random().nextInt(2);
                if (petType == 0) {
                    System.out.println("Send pet protobuf with PetType=" + PetType.DogType);
                    pet = Pet.newBuilder().setPetType(PetType.DogType).setDog(
                        Dog.newBuilder().setMaster(Master.newBuilder().setName("Mickey").setAge(10).build())
                            .setColor("Yellow").setName("Tedy")).build();
                } else {
                    System.out.println("Send pet protobuf with PetType=" + PetType.CatType);
                    pet = Pet.newBuilder().setPetType(PetType.CatType).setCat(
                        Cat.newBuilder().setMaster(Master.newBuilder().setName("Jerry").setAge(10).build())
                            .setColor("BlackAndWhite").setName("Tom")).build();
                }

                ctx.channel().writeAndFlush(pet);
                TimeUnit.SECONDS.sleep(1);
            }
            ctx.close();
        }

        @Override public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("Disconnect to server localhost:9999.");
        }

        @Override public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            System.out.println("An error occourd: " + cause.getMessage());
            ctx.close();
        }
    }
}
