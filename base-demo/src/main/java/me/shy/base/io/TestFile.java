package me.shy.base.io;

import java.io.File;

public class TestFile {
    public static void main(String[] args) {
        File f = new File("e:/A");
        System.out.println(f.getName());
        tree(f, 1);
    }

    private static void tree(File f, int level) {

        String p = "";
        for (int i = 0; i < level; i++) {
            p = p + "   ";
        }

        File[] childs = f.listFiles();
        for (int i = 0; i < childs.length; i++) {
            System.out.println(p + childs[i].getName());
            if (childs[i].isDirectory()) {
                tree(childs[i], level + 1);
            }
        }
    }

}
