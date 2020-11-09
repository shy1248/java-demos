package me.shy.demo.aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: 连接建立完成的回调处理类
 */
public class ConnectCompletionHandler
    implements CompletionHandler<AsynchronousSocketChannel, AsynchronousServerSocketChannel> {

    @Override public void completed(AsynchronousSocketChannel result, AsynchronousServerSocketChannel attachment) {
        // 因为是异步的，因此接受到一个客户端的连接后，必须再调用一次 AsynchronousServerSocketChannel
        // 的 accept 方法，以便能够接受下一个客户端的连接
        attachment.accept(attachment, new ConnectCompletionHandler());
        // 处理系统读IO完成的回调
        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
        result.read(readBuffer, readBuffer, new ReadCompletionHandler(result));

    }

    @Override public void failed(Throwable exc, AsynchronousServerSocketChannel attachment) {
        System.out.println("An error occourd during connect: " + exc.getMessage());
    }
}
