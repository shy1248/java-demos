package me.shy.demo.io;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TestFileCopy {
    public static void main(String[] args) {

        int d;
        FileReader r = null;
        FileWriter w = null;

        try {
            r = new FileReader("D:/TEMP/预割接改正向领区.TXT");
            w = new FileWriter("D:/TEMP/预割接改反向领区.TXT");
        } catch (FileNotFoundException ae) {
            System.out.println("指定的文件不存在!");
            System.exit(-1);
        } catch (IOException ae) {
            System.out.println("系统错误!");
            System.exit(-1);
        }

        try {
            while ((d = r.read()) != -1) {
                w.write(d);
            }
            r.close();
            w.close();
            System.out.println("文件复制成功!");
        } catch (IOException ae) {
            System.out.println("ERROR!");
            System.exit(-1);
        }

    }
}