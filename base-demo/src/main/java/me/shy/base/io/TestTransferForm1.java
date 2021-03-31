package me.shy.base.io;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class TestTransferForm1 {
    public static void main(String[] args) {
        try {
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("e:/Java/JavaExercise/char.txt"));
            osw.write("Microsoftsunapphelp");
            System.out.println(osw.getEncoding());
            osw.close();
            osw = new OutputStreamWriter(new FileOutputStream("e:/Java/JavaExercise/char.txt"), "ISO8859_1");
            osw.write("Microsoftsunapphelp");
            System.out.println(osw.getEncoding());
            osw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
