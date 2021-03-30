package me.shy.demo.base;
public class TestString {
	public static void main(String[] args) {
		String s = "AAaaaGGfffffdjfelrkji%%%60577$^$&%*%*#@$!   HHHjgafFAJDKALD";
		
		/*
		int lCount = 0;
		int uCount = 0;
		int oCount = 0;
		for(int i=0; i<s.length(); i++) {
			if(s.charAt(i) >= 'a' && s.charAt(i) <= 'z') lCount ++;
			if(s.charAt(i) >= 'A' && s.charAt(i) <= 'Z') uCount ++;
			else oCount ++;
		}
		*/
		
		
		/*
		String s1 = "abcdefghijklmnopqrstuvwxyz";
		String s2 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		int lCount = 0;
		int uCount = 0;
		int oCount = 0;
		for(int i=0; i<s.length(); i++) {
			char c = s.charAt(i);
			if(s1.indexOf(c) != -1) lCount ++;
			if(s2.indexOf(c) != -1) uCount ++;
			else  oCount ++;
		}
		*/
		
		
		/*
		int lCount = 0;
		int uCount = 0;
		int oCount = 0;
		for(int i=0; i<s.length(); i++) {
			char c = s.charAt(i);
			if(Character.isLowerCase(c)) lCount ++;
			if(Character.isUpperCase(c)) uCount ++;
			else  oCount ++;
		}
		
		System.out.println(lCount);
		System.out.println(uCount);
		System.out.println(oCount);
		*/
		
		
		
		String ss = "sunjavaphpjavajspjavajavahahajavaaaaaajavajava";
		String ss1 = "java";
		int count = 0;
		int index = -1;
		
		while((index=ss.indexOf(ss1)) != -1) {
			ss = ss.substring(index + ss1.length());
			count ++;
		}
		System.out.println(count);
	}
}