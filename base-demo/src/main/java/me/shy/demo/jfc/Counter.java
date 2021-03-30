package me.shy.demo.jfc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.prefs.Preferences;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileFilter;

public class Counter {

    int i = 0;
    int j = 0;
    int k = 0;
    boolean b = false;

    public static void main(String[] args) {

        new Counter().fileOpen();
    }

    public void fileOpen() {

        JFileChooser jfc = null;
        File[] files = null;
        BufferedReader br = null;
        String line = null;
        String key = null;

        /*
         * 此段用来设置窗口为xp风格
         */
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }


        /*
         * 用来保存路径
         */
        key = "lastPath";
        Preferences pref = Preferences.userRoot().node("/yushuibo/Counter");
        String lastPath = pref.get(key, "");
        if (!lastPath.equals("")) {
            jfc = new JFileChooser(lastPath);
        } else {
            jfc = new JFileChooser();
        }

        jfc.setMultiSelectionEnabled(true);
        jfc.setDialogTitle("请选择需要打开的文件，可选择一个或多个！");
        jfc.setFileFilter(new JavaFileFilter());
        jfc.showOpenDialog(null);
        files = jfc.getSelectedFiles();

        if (files.length == 0) {
            JOptionPane.showMessageDialog(null, "您没有选择任何文件，程序将直接退出！", "注意", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }

        try {
            for (File child : files) {
                pref.put(key, child.getPath());
                br = new BufferedReader(new FileReader(child));
                while ((line = br.readLine()) != null) {
                    parse(line.trim());
                }
            }

            JOptionPane.showMessageDialog(null, "该篇源代码文件共有" + i + "行空行，" + j + "行注释，" + k + "行代码!", "统计完成",
                JOptionPane.INFORMATION_MESSAGE);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                br = null;
            }
        }
    }

    public void parse(String str) {

        if (str.matches("^[\\s&&[^\\n]]*$")) {
            i++;
        } else if (str.startsWith("/*") && !str.endsWith("*/")) {
            j++;
            b = true;
        } else if (str.startsWith("/*") && str.endsWith("*/")) {
            j++;
        } else if (true == b) {
            j++;
            if (str.endsWith("*/")) {
                b = false;
            }
        } else if (str.startsWith("//")) {
            j++;
        } else {
            k++;
        }
    }

    /*
     * 用来过滤文件
     */
    public class JavaFileFilter extends FileFilter {

        public boolean accept(File file) {
			if (file.isDirectory()) {
				return true;
			}
			if (file.getName().endsWith(".java")) {
				return true;
			}
            return false;
        }

        public String getDescription() {
            return "*.java";
        }
    }
}