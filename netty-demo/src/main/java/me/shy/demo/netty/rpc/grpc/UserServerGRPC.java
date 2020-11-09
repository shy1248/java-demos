package me.shy.demo.netty.rpc.grpc;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.io.IOException;
import me.shy.demo.netty.rpc.grpc.UserServiceImpl;
import me.shy.demo.netty.rpc.grpc.userGen.UserServiceGrpc;

/**
 * @Since: 2020/3/21 0:54
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 *
 **/
public class UserServerGRPC {

    private Server server;

    public static void main(String[] args) {
        UserServerGRPC serverGRPC = new UserServerGRPC();
        serverGRPC.start(9999, new UserServiceImpl());
        try {
            serverGRPC.awaitTerminated();
        } catch (InterruptedException e) {
            System.out.println("Server is Interrupted!");
            e.printStackTrace();
        }
    }

    public void start(int port, BindableService service) {
        try {
            server = ServerBuilder.forPort(port).addService(service).build().start();
            System.out.println("Server started, listen on port " + port + " ...");

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("JVM Exit!");
                UserServerGRPC.this.stop();
            }));
        } catch (IOException e) {
            System.out.println("Server start failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void stop() {
        if(null != this.server){
            this.server.shutdown();
            System.out.println("Server has benen shutdown.");
        }
    }

    public void awaitTerminated() throws InterruptedException {
        if(null != this.server){
            System.out.println("Waiting server for shutdown ...");
            this.server.awaitTermination();
        }
    }
}
