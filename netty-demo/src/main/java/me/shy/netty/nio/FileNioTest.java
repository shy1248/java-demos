package me.shy.netty.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Since: 2020/3/21 22:13
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 *
 **/
public class FileNioTest {
    public static void main(String[] args) {
        FileInputStream in = null;
        FileOutputStream out = null;

        try {
            in = new FileInputStream(String.valueOf(FileNioTest.class.getResource("/file_nio_input.txt").getPath()));
            out = new FileOutputStream("file_nio_output.txt");

            FileChannel inChannel = in.getChannel();
            FileChannel outChanel = out.getChannel();

            ByteBuffer buffer = ByteBuffer.allocate(512);

            while (true) {
                // 此处很重要，如果注释掉，当源文件读完后，read时都返回0，会造成死循环写入
                buffer.clear();
                int read = inChannel.read(buffer);
                System.out.println("Read: " + read);
                if (-1 == read) {
                    break;
                }
                // 不能少
                buffer.flip();
                outChanel.write(buffer);
            }

            inChannel.close();
            outChanel.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != in) {
                    in.close();
                }
                if (null != out) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
