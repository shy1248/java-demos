/**
 * @Date        : 2021-02-19 16:39:52
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 插入排序
 */
package me.shy.dsalgo.sort;

public class InsertionSorter<E extends Comparable<? super E>> extends AbstractSorter<E> {

    /**
     * 使用插入排序的算法实现对数组的排序
     *
     * 查找有序表的顺序是从右往左
     * @param array 待排序的数组
     */
    @Override
    void sort(E[] array) {
        // 第一个元素作为有序表，后面所有的元素组成无序表，依次从无序表中取出元素插入到有序表里
        // 所以循环从数组的第二个元素开始，i 的初始值为 1
        for (int i = 1; i < array.length; i++) {
            // 先保存待插入的元素至变量中
            E willBeInserted = array[i];
            // 假定的待插入的索引位置，最开始为当前待插入位置的前一位，即有序表中的最后一个元素
            int willBeInsertedIndex = i - 1;

            // 从右往左循环查找插入位置
            // 1.保证待插入的索引不越界；
            // 2.如果待插入的元素比有序表中最后一个元素还大，说明不需要往前插，直接加入到有序表的最后，在数组里体现就是无需变更位置，因此也不会进入 while 循环；
            // 3.如果待插入的元素比数组里假定的待插入索引位置上元素小，就要先把数组里假定的待插入索引的位置的元素往数组后面移动；
            // 4.同时让假定待插入的索引位置往数组前移动，以便 while 循环比较时获取到有序表中倒数第二个元素并继续与待插入的元素进行大小比较；
            // 5.一直进行循环，直到数组的最前端，或者找到了待插入元素的插入位置（待插入的元素比有序表中的某个元素大）
            // 6.无序担心待插入元素被覆盖，因为上面已经将其保存在变量中；
            // 7.将比较大小的条件反过来，即是从大到小的逆序排列
            while (willBeInsertedIndex >= 0 && willBeInserted.compareTo(array[willBeInsertedIndex]) == -1) {
                // 把数组里假定的待插入索引的位置的元素往数组后面移动
                array[willBeInsertedIndex + 1] = array[willBeInsertedIndex];
                // 让假定待插入的索引位置往数组前移动
                willBeInsertedIndex--;
            }
            // 如果假定待插入的索引位置没有变化，说明没有进入 while 循环，也即位置不变，无需重新赋值
            if (willBeInsertedIndex + 1 != i) {
                // 因为有序表是从右往左查找位置，条件是待插入元素比有序表的某个元素大才退出循环，索引待插入元素应该插入到已查找的的索引的后面的位置
                array[willBeInsertedIndex + 1] = willBeInserted;
            }
        }
    }

}
