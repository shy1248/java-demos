/**
 * @Date        : 2021-02-12 15:06:15
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : A test client.
 */
package me.shy.dp.factorymeathod;

public class TestClient {
    public static void main(String[] args) {
        FoodFactory factory = FoodFactory.getInstance();

        Food poundCake = factory.buildFood(Cake.class, "Pound Cake");
        poundCake.showName();

        Food cheeseCake = factory.buildFood(Cake.class, "Cheese Cake");
        cheeseCake.showName();

        Food angelHairNoodle = factory.buildFood(Noodles.class, "Angel Hair");
        angelHairNoodle.showName();

    }
}
