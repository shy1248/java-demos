/**
 * @Date        : 2021-02-12 22:21:14
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : A test client.
 */
package me.shy.demo.observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class TestClient {
    public static void main(String[] args) {
        // two observers
        BabySetter dad = new Dad();
        BabySetter mum = new Mum();

        // three event sources
        List<Baby> babies = new ArrayList<>();
        Baby tom = new Son();
        tom.setName("Tom");
        // add observers to event source
        tom.addListener(mum).addListener(dad);
        Baby hank = new Son();
        hank.setName("Hank");
        // add observers to event source
        hank.addListener(mum).addListener(dad);
        Baby jerry = new Gril();
        jerry.setName("Jerry");
        // add observers to event source
        jerry.addListener(mum).addListener(dad);
        babies.add(tom);
        babies.add(jerry);
        babies.add(hank);

        // moke event created
        for (Baby baby : babies) {
            try {
                TimeUnit.MILLISECONDS.sleep(new Random().nextInt(500) + 1);
                baby.wakeUP();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
