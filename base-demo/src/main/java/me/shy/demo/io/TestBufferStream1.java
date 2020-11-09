package me.shy.demo.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class TestBufferStream1 {
    public static void main(String[] args) {

        try {
            FileInputStream fis1 = new FileInputStream("E:/Java/JavaExercise/TestFileInputStream.java");
            BufferedInputStream bis1 = new BufferedInputStream(fis1, 10);
            FileOutputStream fis2 = new FileOutputStream("E:/Java/JavaExercise/dat1.txt");
            BufferedOutputStream bis2 = new BufferedOutputStream(fis2, 20);

            System.out.println((char)bis1.read());
            System.out.println((char)bis1.read());
            int c;
            bis1.mark(100);
            for (int i = 0; i < 100 && (c = bis1.read()) != -1; i++) {
                System.out.print((char)c + " ");
                bis2.write(c);
            }
            System.out.println();
            bis1.reset();
            for (int i = 0; i < 100 && (c = bis1.read()) != -1; i++) {
                System.out.print((char)c + " ");
            }
            bis1.close();
            bis2.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}