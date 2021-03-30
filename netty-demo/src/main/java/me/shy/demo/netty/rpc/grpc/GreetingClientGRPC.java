package me.shy.demo.netty.rpc.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.util.Random;
import me.shy.demo.netty.rpc.grpc.greetingGen.GreetingRequest;
import me.shy.demo.netty.rpc.grpc.greetingGen.GreetingResponse;
import me.shy.demo.netty.rpc.grpc.greetingGen.GreetingServiceGrpc;
import me.shy.demo.netty.rpc.grpc.greetingGen.GreetingServiceGrpc.GreetingServiceBlockingStub;

/**
 * @Since: 2020/3/20 22:33
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: a demo application for java grpc client.
 *
 **/
public class GreetingClientGRPC {
    public static void main(String[] args) {
        GreetingClientGRPC clientGRPC = new GreetingClientGRPC();
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9999).usePlaintext().build();
        // 阻塞式客户端，只支持普通的请求，流式请求必须使用非阻塞异步客户端
        GreetingServiceBlockingStub blockingStub = GreetingServiceGrpc.newBlockingStub(channel);

        for(int i=0;i<10;i++){
            String user = clientGRPC.randomName();
            System.out.println("Called Greeting server with: " + user);
            GreetingResponse greetingResponse =
                blockingStub.sayHello(GreetingRequest.newBuilder().setUsername(user).build());
            System.out.println(greetingResponse.getGreetWords());
        }
    }

    private String randomName(){
        String[] names = {"Tom", "Jerry", "Mickey", "Charly", "Jiony"};
        return names[new Random(System.currentTimeMillis()).nextInt(5)];
    }
}
