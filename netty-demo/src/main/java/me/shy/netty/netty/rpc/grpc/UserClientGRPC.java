package me.shy.netty.netty.rpc.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import java.util.List;
import java.util.concurrent.TimeUnit;
import me.shy.netty.netty.rpc.grpc.userGen.User;
import me.shy.netty.netty.rpc.grpc.userGen.UserAge;
import me.shy.netty.netty.rpc.grpc.userGen.UserList;
import me.shy.netty.netty.rpc.grpc.userGen.UserName;
import me.shy.netty.netty.rpc.grpc.userGen.UserServiceGrpc;
import me.shy.netty.netty.rpc.grpc.userGen.UserServiceGrpc.UserServiceStub;
import me.shy.netty.netty.rpc.grpc.userGen.Words;

/**
 * @Since: 2020/3/21 1:03
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 *
 **/
public class UserClientGRPC {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9999).usePlaintext().build();
        // 非阻塞异步客户端，支持流式请求与普通请求
        UserServiceStub stub = UserServiceGrpc.newStub(channel);

        // 对于异步客户端，都需要构建响应回调接口
        // 对于流式请求，调用方法后的返回对象为发送请求数据的入口
        System.out.println("******************************************");
        StreamObserver<User> responseObserver = new StreamObserver<User>() {
            @Override public void onNext(User value) {
                System.out.println("[GetUserByName] - From Server: " + value);
            }

            @Override public void onError(Throwable t) {
                System.out.println("An error occourd: " + t.getMessage());
                t.printStackTrace();
            }

            @Override public void onCompleted() {
                System.out.println("[GetUserByName] - From Server: completed!");
            }
        };
        // 对于普通的请求，普通响应，调用方法无返回值
        stub.getUserByName(UserName.newBuilder().setName("shy").build(), responseObserver);

        System.out.println("******************************************");
        StreamObserver<User> responseObserver1 = new StreamObserver<User>() {
            @Override public void onNext(User value) {
                System.out.println("[GetUsersByAge] - From Server: " + value);
            }

            @Override public void onError(Throwable t) {
                System.out.println("An error occourd: " + t.getMessage());
                t.printStackTrace();
            }

            @Override public void onCompleted() {
                System.out.println("[GetUserByAge] - From Server: completed!");
            }
        };
        // 对于普通的请求，流式响应，调用方法无返回值
        stub.getUsersByAge(UserAge.newBuilder().setAge(20).build(), responseObserver1);

        System.out.println("******************************************");
        StreamObserver<UserList> responseObserver3 = new StreamObserver<UserList>() {
            @Override public void onNext(UserList value) {
                List<User> userList = value.getUserList();
                for (User user : userList) {
                    System.out.println("[GetPackingUsersByAge] - From Server: " + user);
                }
            }

            @Override public void onError(Throwable t) {
                System.out.println("An error occourd: " + t.getMessage());
                t.printStackTrace();
            }

            @Override public void onCompleted() {
                System.out.println("[GetPackingUsersByAge] - From Server: completed!");
            }
        };
        // 对于流式请求，普通响应，调用方法的返回对象为发送请求数据的入口
        StreamObserver<UserAge> packingUsersByAge = stub.getPackingUsersByAge(responseObserver3);
        int[] ages = {20, 30, 40};
        for (int age : ages) {
            packingUsersByAge.onNext(UserAge.newBuilder().setAge(age).build());
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        packingUsersByAge.onCompleted();

        System.out.println("******************************************");
        StreamObserver<Words> responseObserver4 = new StreamObserver<Words>() {
            @Override public void onNext(Words value) {
                System.out.println("[Talk] - From Server: " + value.getWhatSay());
            }

            @Override public void onError(Throwable t) {
                System.out.println("An error occourd: " + t.getMessage());
                t.printStackTrace();
            }

            @Override public void onCompleted() {
                System.out.println("[Talk] - From Client: completed!");
            }

        };

        // 对于流式请求，普通响应，调用方法的返回对象为发送请求数据的入口
        StreamObserver<Words> talk = stub.talk(responseObserver4);

        UserServiceImpl.words.keySet().forEach((word) -> {
            System.out.println("[Talk] - To Server: " + word);
            talk.onNext(Words.newBuilder().setWhatSay(word).build());
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        talk.onNext(Words.newBuilder().setWhatSay("unkonw.").build());

        try {
            TimeUnit.SECONDS.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
