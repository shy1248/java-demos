/**
 * @Date        : 2021-02-12 21:47:02
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : A base class for event source, which can be observered.
 */
package me.shy.dp.observer;

import java.util.ArrayList;
import java.util.List;

public abstract class Baby {
    protected String name;
    protected List<BabySetter> babysetters = new ArrayList<>();

    public Baby addListener(BabySetter babySetter) {
        this.babysetters.add(babySetter);
        return this;
    }

    public Baby removeListener(BabySetter babySetter) {
        this.babysetters.remove(babySetter);
        return this;
    }

    abstract void wakeUP();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
