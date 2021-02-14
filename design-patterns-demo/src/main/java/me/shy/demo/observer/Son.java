/**
 * @Date        : 2021-02-12 21:56:12
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : A specil event source.
 */
package me.shy.demo.observer;

public class Son extends Baby {

    @Override
    void wakeUP() {
        BabyEvent event = new BabyEvent();
        event.setSource(this);
        event.setMessage(String.format("Son %s wakeup ...", this.getName()));
        for (BabySetter babySetter : this.babysetters) {
            babySetter.onWakeUp(event);
        }
    }

}
