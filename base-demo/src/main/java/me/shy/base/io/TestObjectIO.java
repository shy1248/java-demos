package me.shy.base.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class TestObjectIO {
    public static void main(String[] args) throws Exception {
        T t = new T();
        t.j = 35;
        FileOutputStream fos = new FileOutputStream("e:/Java/JavaExercise/ObjectIO.dat");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(t);
        FileInputStream fis = new FileInputStream("e:/Java/JavaExercise/ObjectIO.dat");
        ObjectInputStream ois = new ObjectInputStream(fis);
        T tRead = (T)ois.readObject();
        System.out.print(tRead.i + " " + tRead.j + " " + tRead.d + " " + tRead.c + " " + tRead.k);
    }
}

class T implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    int i = 10;
    int j = 20;
    double d = 2.3;
    char c = 'a';
    transient int k = 9;
}
