package me.shy.demo.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: 传统的 bio 实现 EchoServer，同步阻塞 IO 方式
 * 每收到一个客户端的请求都需要启动一个新的线程处理，服务器线程很快耗尽，无法承受高并发的请求。
 */
public class EchoServer {

    private int port;
    private int backlog;

    public EchoServer(int port) {
        this.port = port;
        this.backlog = 10;
    }

    public EchoServer(int port, int backlog) {
        this.port = port;
        this.backlog = backlog;
    }

    public static void main(String[] args) {
        new EchoServer(8888).start();
    }

    public void start() {
        // 初始化服务端 ServerSocket 对象
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(this.port, this.backlog);
            System.out.println("Server start with listen port: " + this.port + ".");

            // 死循环，等待接受客户端连接
            while (true) {
                // accept 方法会阻塞线程，直到一个连接建立了，才会往下执行
                Socket client = serverSocket.accept();
                System.out.println("A connection accept from " + client.getLocalSocketAddress());
                // 启动新的线程来处理客户端请求
                new Thread(new EchoHandler(client)).start();
            }

        } catch (IOException e) {
            System.out.println(" An error occourd during accept connection: " + e.getMessage());
        } finally {
            if (null != serverSocket) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    System.out.println(" An error occourd during close socket: " + e.getMessage());
                }
            }
        }
    }

    // 处理客户端请求的线程类
    class EchoHandler implements Runnable {

        private Socket client;

        public EchoHandler(Socket client) {
            this.client = client;
        }

        @Override public void run() {
            BufferedReader reader = null;
            PrintWriter writer = null;
            boolean isClientQuit = false;
            try {
                while (!isClientQuit) {
                    // 获取客户端 Socket 的读写流对象
                    reader = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
                    writer = new PrintWriter(this.client.getOutputStream());
                    // 读取客户端的发送的消息
                    String msg = reader.readLine();
                    // 当客户端发送的是 bey！ 是表示客户端要退出
                    if (msg.toUpperCase().equals("BYE!")) {
                        isClientQuit = true;
                    }
                    System.out.println("Server recevied: " + msg);
                    // 格式化当前时间字符串
                    String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd HH:MM:SS"));
                    // 构造响应内容
                    String resp = "[Server - " + now + "]:  " + msg;
                    System.out.println(resp);
                    // 将响应内容发送给客户端
                    writer.println(resp);
                    // 注意需要调用 flush 方法，否则客户端无法接收到
                    writer.flush();
                }
                System.out.println("Client is closed!");
            } catch (IOException e) {
                System.out.println(" An error occourd during receved message: " + e.getMessage());
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

                if (null != this.client) {
                    try {
                        this.client.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
