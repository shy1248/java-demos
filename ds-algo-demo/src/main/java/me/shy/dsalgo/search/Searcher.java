/**
 * @Date        : 2021-02-21 15:00:25
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 常用查找算法实现
 */
package me.shy.dsalgo.search;

import java.util.Arrays;

public class Searcher {

    /**
     * 使用顺序查找在给定的数组中查找指定的元素
     * @param <E> 必须实现 java.lang.Comparable 接口
     * @param array 指定的数组
     * @param element 指定的元素
     * @return 如果指定的数组中包含指定的元素，返回其下标，否则返回 -1
     */
    public static <E extends Comparable<? super E>> int seqSearch(E[] array, E element) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].compareTo(element) == 0) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 使用二分查找在给定的数组中查找指定的元素，采用递归实现
     * @param <E> 必须实现 java.lang.Comparable 接口
     * @param array 指定的数组
     * @param low 数组低位索引，初始值为 0
     * @param high 数组的高位索引，初始值为 array.length - 1
     * @param element 指定的元素
     * @return 如果指定的数组中包含指定的元素，返回其下标，否则返回 -1
     */
    public static <E extends Comparable<? super E>> int binSearch(E[] array, int low, int high, E element) {
        // 低位索引大于了高位索引，说明没找到
        if (low > high) {
            return -1;
        }
        // 计算中间位置索引，采用位移算法效率更高
        int mid = (low + high) >> 1 ;
        if (array[mid].compareTo(element) == 1) { // 需要查找的元素比数组中间的值小，说明要查找的元素一定是在数组的左边，向左递归
            return binSearch(array, low, mid - 1, element);
        } else if (array[mid].compareTo(element) == -1) { // 需要查找的元素比数组中间的值大，说明要查找的元素一定是在数组的右边边，向右递归
            return binSearch(array, mid + 1, high, element);
        } else { // 需要查找的元素与数组中间位置值相等，已经找到，返回索引
            return mid;
        }
    }

    /**
     * 使用插值查找在给定的数组中查找指定的元素，采用递归实现
     * 由于 java 不能进行运算符重载，此处为了简单，只实现 Integer 类型的插值查找序列
     * @param array 指定的数组
     * @param low 数组低位索引，初始值为 0
     * @param high 数组的高位索引，初始值为 array.length - 1
     * @param number 指定的元素
     * @return 如果指定的数组中包含指定的元素，返回其下标，否则返回 -1
     */
    public static int insertSearch(Integer[] array, int low, int high, Integer number) {
        // 低位索引大于了高位索引，说明没找到
        // 条件 number < array[low] || number > array[high] 不可省略
        if (low > high || number < array[low] || number > array[high]) {
            return -1;
        }
        // 计算中间位置索引，只要此处和二分查找不一样
        int mid = low + (high - low) * (number - array[low]) / (array[high] - array[low]);

        if (array[mid] > number) { // 需要查找的元素比数组中间的值小，说明要查找的元素一定是在数组的左边，向左递归
            return insertSearch(array, low, mid - 1, number);
        } else if (array[mid] < number) { // 需要查找的元素比数组中间的值大，说明要查找的元素一定是在数组的右边边，向右递归
            return insertSearch(array, mid + 1, high, number);
        } else { // 需要查找的元素与数组中间位置值相等，已经找到，返回索引
            return mid;
        }
    }

    public static <E extends Comparable<? super E>> int fibSearch(E[] array, E element) {
        int low = 0;
        int high = array.length - 1;
        // 根据数组的长度，找到合适 Fibonacci 数列的长度
        int k = 1;
        while (high > fib(k) - 1) {
            k++;
        }
        // 获取 Fibonacci 数列备用
        int[] fibs = getFiboArray(k);
        // 将原始数组扩长至 Fibonacci 数列中第 k 位元素的长度，新数组多出来的部分初始值为 0，我们需要使用原数组中最后一位（最大值）来填充
        E[] temp = Arrays.copyOf(array, fibs[k]);
        for (int i = high + 1; i < fibs[k]; i++) {
            temp[i] = array[high];
        }
        // 循环查找新的数组 temp，按照 Fibonacci 查找的思路：
        // 整个 temp 数组的长度为：fibs[k]，可以分割为 fibs[k-1]（左）和 fibs[k-2]（右） 两个部分
        while (low <= high) {
            // 每次对比查询后要计算对比元素的位置
            int mid = low + fibs[k - 1] - 1;
            if (temp[mid].compareTo(element) == 1) { // 如果要查找的元素比 mid 位置上的元素小，说明我们要查找的元素位于数组的左边，需要向左查找
                // 向左继续搜索，high 变为 mid -1
                high = mid - 1;
                // 左边部分继续拆分
                k -= 1;
            } else if (temp[mid].compareTo(element) == -1) { // 如果要查找的元素比 mid 位置上的元素大，说明我们要查找的元素位于数组的右边
                // 向右继续搜索，low 变为 mid + 1
                low = mid + 1;
                // 右边部分继续拆分
                k -= 2;
            } else {
                // 因为在新的扩长数组中查找的，所以要判断 mid 的位置是否超出了原始数组的长度，如果超出了直接返回原始数组的最大索引
                if (mid < array.length - 1) {
                    return mid;
                } else {
                    return array.length - 1;
                }
            }
        }
        return -1;
    }

    /**
     * @param k Fibonacci 数列的索引，从 1 开始
     * @return 返回 Fibonacci 数列中第 k 个值
     */
    private static int fib(int k) {
        if (k < 2) {
            return 1;
        } else {
            return fib(k - 1) + fib(k - 2);
        }
    }

    /**
     * 返回一个 Fibonacci 数列的数组
     * 可以直接返回 Fibonacci 第 k 位的值，但是 fibSearch 中会多次使用到该方法，为了效率，可直接返回数组，后续用到某位直接从数组中获取
     * @param k Fibonacci 数列的长度
     * @return
     */
    private static int[] getFiboArray(int k) {
        if (k < 2) {
            throw new IllegalArgumentException("Illegal parameter: " + k);
        }

        int[] fibs = new int[k + 1];
        fibs[0] = 1;
        fibs[1] = 1;
        for (int i = 2; i < k + 1; i++) {
            fibs[i] = fibs[i - 1] + fibs[i - 2];
        }
        return fibs;
    }
}
