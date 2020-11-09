package me.shy.demo.netty.rpc.grpc;

import io.grpc.stub.StreamObserver;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import me.shy.demo.netty.rpc.grpc.userGen.User;
import me.shy.demo.netty.rpc.grpc.userGen.UserAge;
import me.shy.demo.netty.rpc.grpc.userGen.UserList;
import me.shy.demo.netty.rpc.grpc.userGen.UserList.Builder;
import me.shy.demo.netty.rpc.grpc.userGen.UserName;
import me.shy.demo.netty.rpc.grpc.userGen.UserServiceGrpc;
import me.shy.demo.netty.rpc.grpc.userGen.Words;

/**
 * @Since: 2020/3/21 0:16
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 *
 **/

public class UserServiceImpl extends UserServiceGrpc.UserServiceImplBase {
    static final Map<String, String> words = new HashMap<String, String>();

    static {
        words.put("Hello!", "Hi!");
        words.put("What's your name?", "My name is Mickey!");
        words.put("How old are you?", "I'm eighteen!");
        words.put("Nice to meet you!", "So and I!");
        words.put("See you!", "Bye!");
    }

    // 普通请求，普通响应
    @Override public void getUserByName(UserName request, StreamObserver<User> responseObserver) {
        System.out.println("[GetUserByName] - From Client: " + request.getName());
        responseObserver.onNext(User.newBuilder().setName(request.getName()).setAge(25).setLocation("Chengdu").build());
        responseObserver.onCompleted();
    }

    // 普通请求，流式响应
    @Override public void getUsersByAge(UserAge request, StreamObserver<User> responseObserver) {
        System.out.println("[GetUserByage] - From Client: " + request.getAge());
        String[] names = {"Tom", "Jerry", "Mickey"};
        String[] locations = {"Beijing", "Shanghai", "Shenzhen"};
        for (int i = 0; i < names.length; i++) {
            responseObserver
                .onNext(User.newBuilder().setName(names[i]).setAge(request.getAge()).setLocation(locations[i]).build());
        }
        responseObserver.onCompleted();
    }

    // 流式请求，普通响应
    @Override public StreamObserver<UserAge> getPackingUsersByAge(StreamObserver<UserList> responseObserver) {
        return new StreamObserver<UserAge>() {
            private List<Integer> ages = new ArrayList<Integer>();

            @Override public void onNext(UserAge value) {
                int age = value.getAge();
                ages.add(age);
                System.out.println("[GetPackingUsersByAge] - From Client: " + age);
            }

            @Override public void onError(Throwable t) {
                System.out.println("An error occourd: " + t.getMessage());
                t.printStackTrace();
            }

            @Override public void onCompleted() {
                System.out.println("[GetPackingUsersByAge] - From Client: completed!");
                String[] names = {"Tom", "Jerry", "Mickey"};
                String[] locations = {"Beijing", "Shanghai", "Shenzhen"};
                Builder builder = UserList.newBuilder();
                for (int i = 0; i < ages.size(); i++) {
                    builder.addUser(
                        User.newBuilder().setName(names[new Random().nextInt(names.length)]).setAge(ages.get(i))
                            .setLocation(locations[new Random().nextInt(locations.length)]).build());
                }
                UserList userList = builder.build();
                responseObserver.onNext(userList);
                responseObserver.onCompleted();
            }
        };
    }

    // 流式请求，流式响应
    @Override public StreamObserver<Words> talk(StreamObserver<Words> responseObserver) {
        return new StreamObserver<Words>() {
            @Override public void onNext(Words value) {
                String currentSay = value.getWhatSay();
                System.out.println("[Talk] - From Client: " + currentSay);

                String answer = "UNKONW";
                if(words.containsKey(currentSay)){
                    answer = words.get(currentSay);
                }
                System.out.println("[Talk] - To Client: " + answer);
                responseObserver.onNext(Words.newBuilder().setWhatSay(answer).build());
            }

            @Override public void onError(Throwable t) {
                System.out.println("An error occourd: " + t.getMessage());
                t.printStackTrace();
            }

            @Override public void onCompleted() {
                System.out.println("[Talk] - From Server: completed!");
            }
        };
    }
}
