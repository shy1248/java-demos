/**
 * @Date        : 2021-02-12 16:01:20
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : A test client.
 */
package me.shy.demo.abstractfactory;

public class TestClient {
    public static void main(String[] args) {
        // breakfast
        BreakfastFactory breakfastFactory = new BreakfastFactory();
        Food cake = breakfastFactory.newFood("Cheese Cake");
        Drink milk = breakfastFactory.newDrink("Flavers");
        System.out.println(String.format("My breakfast is: Food=%s, Drink=%s", cake.getName(), milk.getName()));

        // dinner
        DinnerFactory dinnerFactory = new DinnerFactory();
        Food noodle = dinnerFactory.newFood("Angel Hair");
        Drink cola = dinnerFactory.newDrink("Coco Cola");
        System.out.println(String.format("My dinner is: Food=%s, Drink=%s", noodle.getName(), cola.getName()));
    }
}
