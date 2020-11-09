package me.shy.demo.aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 */

//CompletionHandler<V,A>
//V-IO操作的结果，这里是write操作写成功的字节数
//A-IO操作附件，
public class WriteCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {

    private AsynchronousSocketChannel asyncSocketChannel;

    public WriteCompletionHandler(AsynchronousSocketChannel asyncSocketChannel) {
        this.asyncSocketChannel = asyncSocketChannel;
    }

    @Override public void completed(Integer result, ByteBuffer attachment) {
        if (result.intValue() == -1) {
            System.out.println("Send data to client error!");
        } else {
            System.out.println("Send data to client successful!");
        }

        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
        asyncSocketChannel.read(readBuffer, readBuffer, new ReadCompletionHandler(asyncSocketChannel));
    }

    @Override public void failed(Throwable exc, ByteBuffer attachment) {
        System.out.println("An error occourd during send data: " + exc.getMessage());
    }
}
