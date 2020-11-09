package me.shy.demo.base;

import java.util.Locale;
import java.util.ResourceBundle;


/**
 * @author yushuibo
 *
 */


public class I18N {
	public static void main(String[] args) {
		ResourceBundle resourceBundle1 = ResourceBundle.getBundle("app", Locale.CHINA);
		System.out.println(resourceBundle1.getString("welcome.msg"));
		ResourceBundle resourceBundle2 = ResourceBundle.getBundle("app", Locale.US);
		System.out.println(resourceBundle2.getString("welcome.msg"));
	}
}
