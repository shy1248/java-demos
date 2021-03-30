package me.shy.demo.base;
public class BinarySearch {
	public static void main(String[] args) {
		Dates[] days = new Dates[5];
		days[0] = new Dates(2003,5,4);
		days[1] = new Dates(2005,9,20);
		days[2] = new Dates(2004,4,6);
		days[3] = new Dates(2007,6,9);
		days[4] = new Dates(2005,9,17);
		Dates d1 = new Dates(2007,6,9);
		bubbleSort(days);
		for(int i=0; i<days.length; i++) {
			System.out.println(days[i]);
		}
		int index = binarySearch(days,d1);
		System.out.println(index);
		
	}
	
	public static void bubbleSort(Dates[] d) {
		Dates temp = new Dates(0,0,0);
		for(int i=d.length-1; i>0 ;i--) {
			for(int j=0; j<i; j++) {
					if(d[j].compare(d[i]) == 1) {	
						temp = d[j];
						d[j] = d[i];
						d[i] = temp;
				 }
			}
		}
	}
	
	public static int binarySearch(Dates a[],Dates d) {
		if(a.length == 0) return -1;
		
		int startPos = 0;
		int endPos = a.length-1;
		int midPos = (startPos + endPos)/2;
		while(endPos >= startPos) {
			if(d.compare(a[midPos]) == 0) return midPos;
			if(d.compare(a[midPos]) == -1) {
				endPos = midPos - 1;
			}
			if(d.compare(a[midPos]) == 1) {
				startPos = midPos + 1;
			}				
			midPos = (startPos + endPos)/2;				
		}
		return -1;
	}
		
}
	
class Dates {
	int year;
	int month;
	int day;
	
	Dates(int y, int m, int d){
		year = y;
		month = m;
		day = d;
	}
	
	public String toString() {
		return "year-month-day: " + year + "-" + month + "-" + day;
	}
	
	public int compare(Dates d) {
		return year > d.year ? 1 
					: year < d.year ? -1 
					: month > d.month ? 1
					: month < d.month ? -1
					: day > d.day ? 1
					: day < d.day ? -1 : 0;
	}
			
}
