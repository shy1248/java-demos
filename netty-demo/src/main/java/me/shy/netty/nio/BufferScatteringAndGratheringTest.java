package me.shy.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * @Since: 2020/3/23 12:07
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 *
 **/
public class BufferScatteringAndGratheringTest {
    public static void main(String[] args) {
        ServerSocketChannel serverSocketChannel = null;
        try {
            serverSocketChannel = ServerSocketChannel.open();
            InetSocketAddress address = new InetSocketAddress(9999);
            serverSocketChannel.socket().bind(address);
            System.out.println("Server started, listen on port 9999 ...");

            // 定义消息长度
            int messageLength = 2 + 3 + 4;
            // 初始化buffer数组
            ByteBuffer[] buffers = new ByteBuffer[3];
            buffers[0] = ByteBuffer.allocate(2);
            buffers[1] = ByteBuffer.allocate(3);
            buffers[2] = ByteBuffer.allocate(4);

            while (true) {
                SocketChannel socketChannle = serverSocketChannel.accept();
                int readBytes = 0;
                // 当收到的字节数小于消息长度时就一直读
                while (readBytes < messageLength) {
                    readBytes += socketChannle.read(buffers);
                    Arrays.asList(buffers).stream().map(
                        buffer -> "[Reading] - Capacity: " + buffer.capacity() + ", Position: " + buffer.position() +
                            ", Limit: "
                            + buffer.limit()).forEach(System.out::println);
                }

                // buffer满后开始往回写，需要先对buffer进行翻转
                Arrays.asList(buffers).forEach(buffer -> {
                    buffer.flip();
                });

                // 往客户端写
                int writeBytes = 0;
                while (writeBytes < messageLength){
                    writeBytes += socketChannle.write(buffers);
                    Arrays.asList(buffers).stream().map(
                        buffer -> "[Writting] - Capacity: " + buffer.capacity() + ", Position: " + buffer.position() +
                            ", Limit: "
                            + buffer.limit()).forEach(System.out::println);
                }

                // 清空buffers
                Arrays.asList(buffers).forEach(buffer -> {
                    buffer.clear();
                });
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(null != serverSocketChannel){
                try {
                    serverSocketChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
