/**
 * @Date        : 2021-02-11 15:40:53
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : -
 */
package me.shy.dp.strategy;

public class Sorter<T> {
    public void sort(T[] array, ComparableStrategy<T> strategy) {
        if(array.length != 0){
            for (int i = 0; i < array.length; i++) {
                for (int j = 0; j < array.length - i - 1; j++) {
                    if (strategy.comparareTo(array[j], array[j + 1]) == 1) {
                        T temp = array[j];
                        array[j] = array[j + 1];
                        array[j + 1] = temp;
                    }
                }
            }
        }
    }
}
