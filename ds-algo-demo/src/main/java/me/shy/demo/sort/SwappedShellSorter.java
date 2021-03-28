/**
 * @Date        : 2021-02-19 19:02:20
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 希尔排序，交换式插入法，效率较低
 */
package me.shy.demo.sort;

public class SwappedShellSorter<E extends Comparable<? super E>> extends AbstractSorter<E> {

    /**
     * 采用希尔排序算法实现数组排序
     * 其中插入采用交换元素法
     *
     * @param array 要排序的数组
     */
    @Override
    void sort(E[] array) {
        // gap 为增量，即分组数，一开始为数组长度的 1/2，意思是将数组中的元素按照每 2 个元素分成一组
        // 然后逐步按照原来 1/2 的大小缩小分组数
        // 注意分组并不是将原始数组中相伶的元素分为一组，而是按照以 gap 为步长来选定元素来作为一组
        // 注意，这个 gap 不一定要是每次递减一半
        for (int gap = array.length / 2; gap > 0; gap /= 2) {
            // i 从 gap 开始，遍历数组元素
            for (int i = gap; i < array.length; i++) {
                // 注意内层循环，j 是递减的，相当于扫描数组是从右至左的，并且 j 的递减步长为分组步数 gap
                // 意思是只对本组内的元素进行比较
                for (int j = i - gap; j >= 0; j -= gap) {
                    if (array[j].compareTo(array[j + gap]) == 1) {
                        // 交换元素
                        Utils.swap(array, j, j + gap);
                    }
                }
            }
        }
    }

}
