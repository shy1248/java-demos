/**
 * @Date        : 2021-02-12 22:08:41
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : Another specil event source.
 */
package me.shy.demo.observer;

public class Gril extends Baby {

    @Override
    void wakeUP() {
        BabyEvent event = new BabyEvent();
        event.setSource(this);
        event.setMessage(String.format("Gril %s wakeup ...", this.getName()));
        for(BabySetter babySetter: this.babysetters) {
            babySetter.onWakeUp(event);
        }
    }

}
