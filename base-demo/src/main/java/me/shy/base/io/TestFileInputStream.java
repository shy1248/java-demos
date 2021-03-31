package me.shy.base.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TestFileInputStream {
    public static void main(String[] args) {

        int b;
        long num = 0;
        FileInputStream in = null;
        try {
            in = new FileInputStream("E:/Java/JavaExercise/TestMap.java");
        } catch (FileNotFoundException ae) {
            System.out.println("The file is not find!");
            System.exit(-1);
        }

        try {
            while ((b = in.read()) != -1) {
                System.out.print((char)b);
                num++;
            }
            in.close();
            System.out.println();
            System.out.println(num + "bits readed!");
        } catch (IOException ae) {
            System.out.println("ERROR!");
            System.exit(-1);
        }

    }
}
