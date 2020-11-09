package me.shy.demo.base;

public class Fibonacci {


	public static void main(String[] args) {
		
		System.out.println(fibByNormal(40));
		System.out.println(fibByRecursion(40));
	}
	
	public static int fibByNormal (int index){
		
		if (index < 1){
			System.out.println("Invalid paramter!");
			System.exit(0);
		}
		int f1 = 1;
		int f2 = 1;
		int sum = 0;
		if(index == 1 || index == 2){
			return 1;
		}else{
			for(int i=0;i<index-2;i++){
				sum = f1 + f2;
				f1 = f2;
				f2 = sum;
			}
			return sum;
		}
	}

	public static int fibByRecursion(int i) {
		if (i == 1 || i == 2){
			return 1;
		}else{
			return fibByRecursion(i - 1) + fibByRecursion(i - 2);
		}
	}

}
