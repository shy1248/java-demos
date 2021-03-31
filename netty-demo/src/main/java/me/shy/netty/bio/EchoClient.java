package me.shy.netty.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 */
public class EchoClient {

    public static void main(String[] args) {
        final int port = 8888;
        Socket socket = null;
        BufferedReader reader = null;
        PrintWriter writer = null;
        Scanner scanner = null;

        try {
            // 创建客户端 Socket 对象，并获取 socket 读写流
            socket = new Socket("127.0.0.1", port);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream());

            System.out.println("Connect to server: 127.0.0.1:" + port + ".");
            boolean isQuit = false;
            while (!isQuit) {
                System.out.println("Enter you message: ");
                // 获取控制台输入流，可以实时交互输入
                scanner = new Scanner(System.in);
                String whatSay = null;

                // 当有输入时，获取输入内容
                if (scanner.hasNextLine()) {
                    whatSay = scanner.nextLine();
                    // 判断输入内容是否为退出指令
                    if (whatSay.toUpperCase().equals(":QUIT")) {
                        whatSay = "Bye!";
                        isQuit = true;
                        scanner.close();
                    }

                    // 如果输入的内容为空就忽略
                    if (whatSay.trim().equals("")) {
                        continue;
                    }

                    // 发送输入的内容给客户端
                    writer.println(whatSay);
                    // 需要调用 flush，否则服务端无法收到消息
                    writer.flush();
                    System.out.println("Client: send " + whatSay + " to server.");
                    // 读取服务器响应消息并打印
                    String resp = reader.readLine();
                    System.out.println(resp);
                }
            }
        } catch (IOException e) {
            System.out
                .println("An error occourd during connect to server with 127.0.0.1:" + port + ": " + e.getMessage());
        } finally {
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (null != writer) {
                writer.close();
            }

            if (null != socket) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
