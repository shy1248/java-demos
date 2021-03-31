/**
 * @Since: 2019-07-24 22:44:39
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 * @LastTime: 2019-07-24 22:50:30
 */

package me.shy.base.patterns.simplefactory;

public class PieChart implements Chart {

    public PieChart() {
        System.out.println("PieChart initilized!");
    }

    @Override public void display() {
        System.out.println("PieChart displayed!");
    }
}
