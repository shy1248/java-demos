/**
 * @Since: 2019-07-24 22:46:26
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 * @LastTime: 2019-07-24 22:47:54
 */

package me.shy.base.patterns.simplefactory;

public class LineChart implements Chart {

    public LineChart() {
        System.out.println("LineChart initilized!");
    }

    @Override public void display() {
        System.out.println("LineChart displayed!");
    }

}

