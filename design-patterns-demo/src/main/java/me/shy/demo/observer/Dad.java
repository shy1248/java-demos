/**
 * @Date        : 2021-02-12 22:12:43
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : A sepcil observer.
 */
package me.shy.demo.observer;

public class Dad implements BabySetter {

    @Override
    public void onWakeUp(BabyEvent e) {
        System.out.println(String.format("Dad recevied message: %s", e.getMessage()));
        this.feed();
    }

    public void feed() {
        System.out.println("Dad feeding ...");
    }

}
