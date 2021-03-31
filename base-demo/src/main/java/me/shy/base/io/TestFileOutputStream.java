package me.shy.base.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class TestFileOutputStream {
    public static void main(String[] args) {

        int d;
        FileInputStream in = null;
        FileOutputStream ou = null;

        try {
            ou = new FileOutputStream("E:/Java/JavaExercise/TestMap__1.java");
            in = new FileInputStream("E:/Java/JavaExercise/TestMap.java");
        } catch (FileNotFoundException ae) {
            System.out.println("The file is not found!");
            System.exit(-1);
        }

        try {
            while ((d = in.read()) != -1) {
                ou.write((char)d);
            }
            System.out.println("�ļ����Ƴɹ�!");
            in.close();
            ou.close();
        } catch (IOException ae) {
            System.out.println("ERROR!");
            System.exit(-1);
        }
    }
}
