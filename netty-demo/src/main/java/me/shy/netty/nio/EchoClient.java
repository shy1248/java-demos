package me.shy.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
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
        InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 8888);
        SocketChannel socketChannel = null;
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        try {
            // 创建客户端 socket 通道
            socketChannel = SocketChannel.open(socketAddress);
            System.out.println("Connect to server: 127.0.0.1:8888.");

            boolean isQuit = false;

            while (!isQuit) {
                System.out.println("Enter you message: ");
                // 获取控制台输入流，可以实时交互输入
                Scanner scanner = new Scanner(System.in);
                String whatSay = null;
                // 当有输入时，获取输入内容
                if (scanner.hasNextLine()) {
                    whatSay = scanner.nextLine();
                }
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

                // 发送消息给服务器
                buffer.clear();
                buffer.put(whatSay.getBytes());
                buffer.flip();
                socketChannel.write(buffer);
                buffer.clear();
                System.out.println("Client: send " + whatSay + " to server.");

                // 接受服务器响应
                // 清空客户端读缓冲区
                buffer.clear();
                // 开始读取服务器响应内容至读缓冲区
                int len = socketChannel.read(buffer);
                // len 为 -1 表示已经读完
                if (len == -1) {
                    socketChannel.close();
                }
                // 复位读缓冲区，否则可能读到的内容不完整
                buffer.flip();
                // 创建字节数组接收读缓冲区的内容
                byte[] bytes = new byte[buffer.remaining()];
                // 读取读缓冲区内容值字节数组中
                buffer.get(bytes);
                // 打印消息
                System.out.println(new String(bytes).trim());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
