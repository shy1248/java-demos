package me.shy.demo.aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 */

//CompletionHandler<V,A>
//V-IO操作的结果，这里是read操作成功读取的字节数
//A-IO操作附件，由于ConnectCompleteHandler中调用asyncSocketChannel.read方法时
//	传入了ByteBuffer，所以这里为ByteBuffer
public class ReadCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {

    private AsynchronousSocketChannel asyncSocketChannel;

    public ReadCompletionHandler(AsynchronousSocketChannel asyncSocketChannel) {
        this.asyncSocketChannel = asyncSocketChannel;
    }

    @Override public void completed(Integer result, ByteBuffer attachment) {
        attachment.flip();
        byte[] bytes = new byte[attachment.remaining()];
        attachment.get(bytes);
        String msg = new String(bytes, StandardCharsets.UTF_8);
        System.out.println("Server recevied: " + msg);
        // 格式化当前时间字符串
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd HH:MM:SS"));
        // 构造响应内容
        String resp = "[Server - " + now + "]:  " + msg;
        System.out.println(resp);

        byte[] respBytes = resp.getBytes(StandardCharsets.UTF_8);
        ByteBuffer writeBuffer = ByteBuffer.allocate(respBytes.length);
        writeBuffer.put(respBytes);
        writeBuffer.flip();
        asyncSocketChannel.write(writeBuffer, writeBuffer, new WriteCompletionHandler(asyncSocketChannel));
    }

    @Override public void failed(Throwable exc, ByteBuffer attachment) {
        System.out.println("An error occourd during read data: " + exc.getMessage());
    }
}
