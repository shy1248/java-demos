package me.shy.base.oo;

public class Test {

	/**
	 * 农场里有头小母牛，每年生头小母牛，小母牛五岁以后每年都会生头小母牛，问二十年后
	 * 农场里总共有几头牛
	 * @param args
	 */
	public static void main(String[] args) {
		Cow cow = new Cow();
		cow.setAge(5);
		Farm farm = new Farm();
		farm.getCows().add(cow);
		int years = 20;
		for(int i=1;i<=years;i++){
			for(int j=0;j<farm.getCows().size();j++){
				farm.getCows().get(j).setAge(farm.getCows().get(j).getAge()+1);
				if(farm.getCows().get(j).getAge()>=5){
					Cow cowChild = farm.getCows().get(j).bear();
					farm.getCows().add(cowChild);
				}
			}
		}
		System.out.println(farm.getCows().size());
	}
}
