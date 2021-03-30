package me.shy.demo.base;
public class SelectionSort {
	public static void main(String[] args) {
		Date[] days = new Date[5];
		days[0] = new Date(2008,9,10);
		days[1] = new Date(2004,10,20);
		days[2] = new Date(2005,1,9);
		days[3] = new Date(2006,5,8);
		days[4] = new Date(2007,9,10);
		selectionSort(days);
		print(days);
	}
	
	private static Date[] selectionSort(Date[] a) {
		for(int i=0; i<a.length; i++) {
			for(int j=i+1; j<a.length; j++) {
				if(a[j].compare(a[i]) < 0) {
					Date temp = a[i];
					a[i] = a[j];
					a[j] = temp;
				}
			}
		}
		return a;
	}
	
	private static void print(Date[] a) {
		for(int i=0; i<a.length; i++) {
			System.out.println(a[i]);
		}
	}

}

 class Date {
	int year;
	int month;
	int day;
	Date(int y, int m, int d) {
		year = y;
		month = m;
		day = d;
	}
	
  int compare(Date d) {
		return year > d.year ? 1
		: year < d.year ? -1 
		: month > d.month ? 1
	  : month < d.month ? -1 
	  : day > d.day ? 1 
	  : day < d.day ? -1 : 0;
	}
	
	public String toString() {
		return "year:month:day----" + year +"-"+ month +"-" + day;
	}
	
}
