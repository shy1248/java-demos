package me.shy.demo.netty.serializationAndZip;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 */
public class GzipUtil {

    /**
     * Gzip 压缩
     * @param source
     * @return
     * @throws IOException
     */
    public static byte[] zip(byte[] source) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(baos);
        gzip.write(source, 0, source.length);
        if (null != gzip) {
            gzip.close();
        }
        return baos.toByteArray();
    }

    /**
     * Gzip 解压
     * @param source
     * @return
     * @throws IOException
     */
    public static byte[] unzip(byte[] source) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ByteArrayInputStream bais = new ByteArrayInputStream(source);
        GZIPInputStream gzip = new GZIPInputStream(bais);
        byte[] buffer = new byte[1024];
        int offset = -1;
        while ((offset = gzip.read(buffer)) != -1) {
            baos.write(buffer, 0, offset);
        }
        if (null != gzip) {
            gzip.close();
        }
        return baos.toByteArray();
    }

    public static void main(String[] args) throws IOException {
        FileInputStream fis = new FileInputStream("D:\\demos.workspace\\people.json");
        FileOutputStream fos = new FileOutputStream("D:\\demos.workspace\\people.json.gz");
        System.out.println("File length before zip is: " + fis.available());
        byte[] source = new byte[fis.available()];
        fis.read(source);
        byte[] target = GzipUtil.zip(source);
        fos.write(target);
        System.out.println("File length after zip is: " + target.length);
        fis.close();
        fos.close();
    }
}
