/**
 * @Since: 2019-07-24 22:48:46
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 * @LastTime: 2019-07-24 22:50:23
 */

package me.shy.demo.patterns.simplefactory;

public class HistogramChart implements Chart {

    public HistogramChart() {
        System.out.println("HistogramChart initilized!");
    }

    @Override public void display() {
        System.out.println("HistogramChart displayed!");
    }
}

