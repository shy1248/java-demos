/**
 * @Date        : 2021-02-19 13:51:42
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 冒泡排序，时间复杂度为 O(n^2)
 */
package me.shy.demo.sort;

public class BubbleSorter<E extends Comparable<? super E>> extends AbstractSorter<E> {

    /**
     * 使用冒泡排序算法实现对数组进行排序
     * @param array 要排序的数组
     */
    @Override
    void sort(E[] array) {
        // 总共要进行数组元素个数减1（array.length - 1）趟排序比较
        for (int i = 0; i < array.length - 1; i++) {
            // 优化标志位，如果某趟遍历元素中未发生任何交换，说明数据已经有序，可提前终止
            boolean hasSwapped = false;
            // 每一趟中都要对 array.length - 1 - i 个元素进行遍历，因为每趟遍历后都能确定序列中最后一个元素
            for (int j = 0; j < array.length - 1 - i; j++) {
                // 要比较的对象必须要实现 java.lang.Comparable 接口
                // compareTo 方法返回 1，说明当前对象与要比较的对象大，返回 0 表示一样大，返回 -1 表示比要比较的对象小
                // 此处为从小到大的顺序进行排序
                if (1 == array[j].compareTo(array[j + 1])) {
                    // 如果当前对象与要比较的对象大，则进行位置交换
                    Utils.swap(array, j, j + 1);
                    hasSwapped = true;
                }

                // System.out.println(Arrays.toString(array));
            }
            // 如果某趟遍历元素中未发生任何交换，提前终止算法
            if (!hasSwapped) {
                break;
            }
        }
    }

}
