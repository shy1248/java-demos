package me.shy.demo.io;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class TestPrintStream {
    public static void main(String[] args) {
        PrintStream ps = null;
        try {
            FileOutputStream fos = new FileOutputStream("e:/Java/JavaExercise/bak.dat");
            ps = new PrintStream(fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (ps != null) {
            System.setOut(ps);
        }
        int ln = 0;
        for (char c = 0; c < 65535; c++) {
            System.out.print(c + " ");
            if (ln++ >= 100) {
                System.out.println();
                ln = 0;
            }
        }
        ps.close();
    }
}