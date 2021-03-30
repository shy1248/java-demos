package me.shy.demo.base;
public class TestSelectionSort {
	public static void main(String[] args) {
		int a[] = new int[args.length];
		for (int i=0; i<args.length; i++) {
			a[i] = Integer.parseInt(args[i]);
		}
		print(a);
		selectionsort(a);
		print(a);
		
	}
	
	private static void print(int[] a) {
		for(int i=0; i<a.length; i++) {
			System.out.print(a[i] + " ");
		}
		System.out.println();
	}
	
	private static void selectionsort(int[] a) {
		for(int i=0; i<a.length; i++) {
			for(int j=i+1; j<a.length; j++) {
				if(a[j]<a[i]) {
					int temp;
					temp = a[i];
					a[i] = a[j];
					a[j] = temp;
				}
			}
		}
	}
	
}
