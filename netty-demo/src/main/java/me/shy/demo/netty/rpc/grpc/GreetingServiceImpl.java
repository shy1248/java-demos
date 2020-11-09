package me.shy.demo.netty.rpc.grpc;

import io.grpc.stub.StreamObserver;
import me.shy.demo.netty.rpc.grpc.greetingGen.GreetingRequest;
import me.shy.demo.netty.rpc.grpc.greetingGen.GreetingResponse;
import me.shy.demo.netty.rpc.grpc.greetingGen.GreetingServiceGrpc;

/**
 * @Since: 2020/3/20 22:16
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 *
 **/
public class GreetingServiceImpl extends GreetingServiceGrpc.GreetingServiceImplBase {
    @Override public void sayHello(GreetingRequest request, StreamObserver<GreetingResponse> responseObserver) {
        System.out.println("Called sayHello method with prama: " + request.getUsername());
        // 响应给客户端
        responseObserver.onNext(GreetingResponse.newBuilder().setGreetWords("Server: Hello, " + request.getUsername()).build());
        // 告诉客户端响应完毕
        responseObserver.onCompleted();
    }
}
