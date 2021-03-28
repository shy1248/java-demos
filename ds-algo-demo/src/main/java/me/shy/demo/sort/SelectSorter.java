/**
 * @Date        : 2021-02-19 15:34:56
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 选择排序，时间复杂度为 O(n^2)
 */
package me.shy.demo.sort;

public class SelectSorter<E extends Comparable<? super E>> extends AbstractSorter<E> {

    /**
     * 使用选择排序算法的实现对数组进行排序
     * @param array 要排序的数组
     */
    @Override
    void sort(E[] array) {
        // 总共要进行 array.length - 1 轮排序
        for (int i = 0; i < array.length - 1; i++) {
            // 获取第 i-1 个元素，与序列中其后的元素进行比较，找出后面序列中的最小元素的索引并记录索引位置
            // 当与其后的所有元素比较完毕后，将其与第 i-1 个元素进行交换即可

            // 先假定第 i-1 个元素是最小的
            int minIndex = i;
            // 将第 i-1 个元素与后的元素逐个进行比较，注意每一轮排序 j 的初始值
            for (int j = i + 1; j < array.length; j++) {
                // 如果当前选定的元素比其后序列中某个元素大，说明假定的元素不是最小的，需要更新最小元素的以及其索引
                // array[minIndex] 即是获取到当前查找到的最小元素
                if (1 == array[minIndex].compareTo(array[j])) {
                    minIndex = j;
                }
            }
            // 如果 minIndex 等于 i，表示当前假定元素即为后续序列中最小元素，不需要交换
            if (minIndex != i) {
                Utils.swap(array, i, minIndex);
            }
        }
    }
}
