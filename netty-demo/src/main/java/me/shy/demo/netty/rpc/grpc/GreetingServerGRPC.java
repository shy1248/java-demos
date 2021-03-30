package me.shy.demo.netty.rpc.grpc;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.io.IOException;

/**
 * @Since: 2020/3/20 22:19
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: a demo application for java grpc server.
 *
 **/
public class GreetingServerGRPC {
    private Server server;

    public static void main(String[] args) {
        GreetingServerGRPC serverGRPC = new GreetingServerGRPC();
        serverGRPC.start(9999, new GreetingServiceImpl());
        try {
            serverGRPC.awaitTerminated();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void start(int port, BindableService service) {
        try {
            // 构建Server并启动
            this.server = ServerBuilder.forPort(port).addService(service).build().start();
            System.out.println("Setver started, listen on port " + port);
            // 绑定JVM退出钩子事件，以确保Server退出
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("JVM exit!");
                GreetingServerGRPC.this.stop();
            }));
        } catch (IOException e) {
            System.out.println("Server starting failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void stop() {
        if(null != this.server){
            // 关闭服务
            this.server.shutdown();
            System.out.println("Server has been shutdown!");
        }
    }

    public void awaitTerminated() throws InterruptedException {
        if(null != this.server){
            // 等待服务退出，因为底层使用了netty的异步模型，必须要调用这个函数，否则服务启动后就自动退出了
            System.out.println("Waiting for server to shutdown ...");
            this.server.awaitTermination();
            // 设置3000毫秒后服务器退出
            // this.server.awaitTermination(3000, TimeUnit.MILLISECONDS);
        }
    }
}
