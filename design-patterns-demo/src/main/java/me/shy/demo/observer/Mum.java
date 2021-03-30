/**
 * @Date        : 2021-02-12 22:16:21
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : Another specil observer.
 */
package me.shy.demo.observer;

public class Mum implements BabySetter {

    @Override
    public void onWakeUp(BabyEvent e) {
        System.out.println(String.format("Mum recevied message: %s", e.getMessage()));
        this.hug();
    }

    public void hug() {
        System.out.println("Mum hugging...");
    }
}
