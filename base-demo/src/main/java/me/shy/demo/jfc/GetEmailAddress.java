package me.shy.demo.jfc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class GetEmailAddress {

    /*private static final LookAndFeel WindowsLookAndFeel = null;*/

    public static void main(String[] args) {

        new GetEmailAddress().fileOpen();
    }

    public void fileOpen() {

	/*	UIManager uim = new UIManager();
		//LookAndFeel = new LookAndFeel();
		try {
			uim.setLookAndFeel(WindowsLookAndFeel);
		} catch (UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
*/
        JFileChooser jfc = null;
        BufferedReader br = null;
        String line = null;
        File file = null;

        jfc = new JFileChooser();
        jfc.showOpenDialog(null);
        file = jfc.getSelectedFile();

        try {
            try {
                br = new BufferedReader(new FileReader(file));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "您没有选择任何文件，程序将直接退出！", "注意", JOptionPane.ERROR_MESSAGE);
            System.exit(0);

        }
        try {
            while ((line = br.readLine()) != null) {
                parse(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parse(String str) {
        Pattern pa = Pattern.compile("[\\w[.-]]+@[\\w[.-]]+\\.[\\w+[.-]]+");
        Matcher ma = pa.matcher(str);
        while (ma.find()) {
            System.out.println(ma.group());
        }
    }
}