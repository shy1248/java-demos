package me.shy.demo.base;
public class TestBubbleSort {
	public static void main(String[] args) {
		int[] a = new int[args.length];
		for(int i=0; i<args.length; i++) {
			a[i] = Integer.parseInt(args[i]);
		}
		print(a);
		bubbleSort(a);
		print(a);
	}
	
	private static int[] bubbleSort(int[] a) {
		for(int i=a.length-1; i>0; i--){
			for(int j=0; j<i; j++) {
				if(a[i] < a[j]) {
					int temp = a[j];
					a[j] = a[i];
					a[i] = temp;
				}
			}
		}
		return a;
	}
	
	private static void print(int[] a) {
		for(int i=0; i<a.length; i++) {
			System.out.print(a[i] + " ");
		}
		System.out.println();
	}
}