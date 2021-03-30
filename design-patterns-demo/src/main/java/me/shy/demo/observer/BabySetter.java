/**
 * @Date        : 2021-02-12 21:47:55
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : A base class for Observer, which can watching BabyEvent.
 */
package me.shy.demo.observer;

public interface BabySetter {
    void onWakeUp(BabyEvent e);
}
