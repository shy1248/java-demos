package me.shy.demo.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TestBufferStream2 {
    public static void main(String[] args) {
        try {
            FileWriter fw = new FileWriter("E:/Java/JavaExercise/dat2.txt");
            BufferedWriter bw = new BufferedWriter(fw, 100);
            FileReader fr = new FileReader("E:/Java/JavaExercise/dat2.txt");
            BufferedReader br = new BufferedReader(fr);
            String s = null;

            for (int i = 0; i < 100; i++) {
                s = String.valueOf(Math.random());
                bw.write(s);
                bw.newLine();
            }
            bw.flush(); //如果不用这个方法，就只写到内存，不会写到硬盘，也就是Data2里还是空的，顾打印出来的也是空的

            while ((s = br.readLine()) != null) {

                System.out.println(s);
            }
            br.close();
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}