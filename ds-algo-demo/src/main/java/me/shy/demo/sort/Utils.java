/**
 * @Date        : 2021-02-19 14:07:31
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : -
 */
package me.shy.demo.sort;

public class Utils {

    /**
     * 交换数组中两个索引位置的值
     * @param <E>
     * @param array 要交换的原始数组
     * @param i
     * @param j
     */
    public static <E> void swap(E[] array, int i, int j) {
        E temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

}
