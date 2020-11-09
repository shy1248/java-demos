package me.shy.demo.oo;

import java.util.ArrayList;
import java.util.List;

public class Farm {
    private List<Cow> cows = new ArrayList<Cow>();

    public List<Cow> getCows() {
        return cows;
    }

    public void setCows(List<Cow> cows) {
        this.cows = cows;
    }
}
