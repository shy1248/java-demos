package me.shy.demo.base;
public class Num3Quit1 {
	public static void main(String[] args) {
		KidCircle kc = new KidCircle(500);

		Kid k = kc.first;
		int countNum = 0;
		while(kc.count > 1) {
				countNum ++;
				if(countNum == 3) {
					countNum = 0;
					kc.delete(k);
				}
				
				k = k.right;
			}
		
			System.out.println(kc.first.id);
	}
}

class Kid {
	int id;
	Kid left;
	Kid right;
	/*
	Kid(int i) {
		id = i;
	}
	*/
}

class KidCircle {
	int count = 0;
	Kid first;
	Kid last;
	
	KidCircle(int n) {
		for(int i=0; i<n; i++) {
			add();
		}
	}
		
	void add() {
		Kid k = new Kid();
		k.id = count;
		if(count == 0) {
			first = k;
			last = k;
			k.left = k;
			k.right = k;
		}else {
			k.left = last;
			first.left = k;
			k.right = first;
			last.right = k;
			last = k;
		}
		count ++;
	}
	
	void delete(Kid k) {
		if(count <= 0) {
			//System.out.println("There is no Kid in the KidCircle!");
			return;
		}else if(count == 1)  {
			first = last = null;
		}else {
			k.left.right = k.right;
			k.right.left = k.left;
			
			if(k == first) {
				first = k.right;
			}else if(k == last) {
				last = k.left;
			}
		}
		count --;
	}
}