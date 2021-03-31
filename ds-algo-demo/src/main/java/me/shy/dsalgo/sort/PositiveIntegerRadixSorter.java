/**
 * @Date        : 2021-02-20 15:27:50
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : -
 */
package me.shy.dsalgo.sort;

public class PositiveIntegerRadixSorter extends RadixSorter<Integer> {


    public PositiveIntegerRadixSorter(int bucketsNumber) {
        super(bucketsNumber);
    }

    /**
     * 获取非负整数数组中最大数的位数
     * @param array 待排序的数组
     * @return
     */
    @Override
    int getKeySize(Integer[] array) {
        int max = 0;
        for (int i = 0; i < array.length; i++) {
            int current = array[i];
            if (current > max) {
                max = current;
            }
        }
        return (max + "").length();
    }

    /**
     * 按照个、十、百、千 ... 的顺序返回非负整数对应位置上的数字
     * @param index 数位的索引，0表示个位，1表示十为 ...
     */
    @Override
    int getKey(Integer integer, int index) {
        return integer % (int) Math.pow(10, index + 1) / (int) Math.pow(10, index);
    }

}
