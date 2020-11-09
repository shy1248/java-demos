package me.shy.demo.base;
public class String2Array {
	public static void main(String[] args) {
		Double[][] d;
		String s = "1,2;3,4,5;6,7,8";
		String[] s1 = s.split(";");
		d = new Double[s1.length][];
		for(int i=0; i<s1.length; i++) {	
			String[] s2 = s1[i].split(",");    //
			//System.out.println(s1[i] + " ");		���ԣ���s1��û�зֲ�ɹ�
			d[i] = new Double[s2.length];
			for(int j=0; j<s2.length; j++) {
				//System.out.println(s2[j] + " ");      ���ԣ���s2��û�зֲ�ɹ�
				d[i][j] = Double.parseDouble(s2[j]);
			}
			
		}
		
		for(int i=0; i<d.length; i++) {
			for(int j=0; j<d[i].length; j++) {
				System.out.print(d[i][j] + " ");
			}
			System.out.println();
		}
		
	}
}