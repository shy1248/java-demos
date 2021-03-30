/**
 * @Date        : 2021-02-20 10:38:40
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 基数排序
 */
package me.shy.demo.sort;

import java.util.LinkedList;
import java.util.List;

public abstract class RadixSorter<E extends Comparable<? super E>> extends AbstractSorter<E> {
    private int bucketsNumber;

    public RadixSorter(int bucketsNumber) {
        this.bucketsNumber = bucketsNumber;
    }

    @Override
    void sort(E[] array) {
        // 计算数组中的关键字个数
        int keySize = getKeySize(array);
        // 关键字个数必须小于或等于桶的个数
        if (keySize > bucketsNumber) {
            throw new RuntimeException("Buckets " + bucketsNumber + " is less than key size.");
        }
        // 创建桶的列表，因为桶中要放多少个元素不确定（最坏情况下，所有的元素都放在了一个桶中，因此最大为 array.length）
        // 所以桶的结构设计为链表比较合适
        List<LinkedList<E>> buckets = new LinkedList<LinkedList<E>>();
        for (int i = 0; i < bucketsNumber; i++) {
            buckets.add(new LinkedList<>());
        }
        // 根据待比较的关键字（关键字的优先级以及按照从低到高的顺序排列），扫描待排序的序列，将元素分配到对应的桶中
        for (int i = 0; i < keySize; i++) {
            for (int j = 0; j < array.length; j++) {
                // 获取元素的关键字
                int key = getKey(array[j], i);
                // 获取关键字对应的桶，并将元素分配至桶中
                buckets.get(key).add(array[j]);
            }

            // 将桶中的元素依次复制回原始数组
            // 原始数组的索引
            int index = 0;
            // 循环所有的桶
            for (int k = 0; k < bucketsNumber; k++) {
                // 获取关键字对应的桶
                List<E> bucket = buckets.get(k);
                // 只要桶中还有元素，就将其复制到原始数组中并删除桶中的元素
                while (bucket.size() > 0) {
                    array[index++] = bucket.remove(0);
                }
            }
        }

    }

    /**
     * 从待排序的序列中获取待比较的关键字的个数
     * @param <E>
     * @param array 待排序的数组
     * @return
     */
    abstract int getKeySize(E[] array);

    /**
     * 从待排序的序列元素的对象中获取关键字
     * @param <E>
     * @param e 待排序的序列元素
     * @param index 关键字的优先级索引
     * @return
     */
    abstract int getKey(E e, int index);

    public int getBucketsNumber() {
        return bucketsNumber;
    }
}
