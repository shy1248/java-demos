/**
 * @Date        : 2021-02-19 21:00:52
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 快速排序
 */
package me.shy.dsalgo.sort;

public class QuickSorter<E extends Comparable<? super E>> extends AbstractSorter<E> {

    @Override
    void sort(E[] array) {
        quickSort(array, 0, array.length - 1);
    }

    /**
     * 递归实现快速排序算法对数组进行排序
     * @param <E>
     * @param array
     * @param left
     * @param right
     */
    private void quickSort(E[] array, int left, int right) {
        // 比较的基准值，可以是序列中任何一个元素，一般选择开头、结尾或者最中间的元素，此处选择数组中间的元素
        E pivot = array[(left + right) / 2];
        // E pivot = array[left];
        // 左侧遍历索引，从左往由遍历
        int leftIdx = left;
        // 右侧遍历索引，从右往左遍历
        int rightInx = right;

        // 外层循环确保了经过此次排序后，序列中左边的元素都比所选择的基准值小，序列中右边的元素都比基准值大
        while (leftIdx < rightInx) {
            // 在序列从左往右遍历，直到发现一个元素大于或者等于基准值停止
            while (array[leftIdx].compareTo(pivot) == -1) {
                leftIdx += 1;
            }
            // 在序列从右往左遍历，直到发现一个元素小于或者等于基准值停止
            while (array[rightInx].compareTo(pivot) == 1) {
                rightInx -= 1;
            }

            // 序列左右两个方向上均找到一个满足条件的值（即左方向上找到一个大于或等于基准值的元素，右方向上找到一个小于或等于基准值的元素）
            // 此时如果左方索引大于或等于右向索引，说明已经搜索过界了, 序列左边的元素均已小于基准值，右边均已大于基准值，直接终止，不需要交换；
            // 否则将左右两个方向上的值进行交换
            if (leftIdx >= rightInx) {
                break;
            }
            // 交换找到满足条件的值
            Utils.swap(array, leftIdx, rightInx);

            // 交换之后，如果发现左向索引位置的值与基准值相等，则需要将右向索引朝左边移动一位
            if (array[leftIdx].compareTo(pivot) == 0) {
                rightInx -= 1;
            }
            // 交换之后，如果发现右向索引位置的值与基准值相等，则需要将左向索引朝右边移动一位
            if (array[rightInx].compareTo(pivot) == 0) {
                leftIdx += 1;
            }
        }

        // 防止出现死循环，无限递归造成栈溢出
        if (leftIdx == rightInx) {
            leftIdx += 1;
            rightInx -= 1;
        }

        // 向左递归
        if (left < rightInx) {
            quickSort(array, left, rightInx);
        }

        // 向右递归
        if (right > leftIdx) {
            quickSort(array, leftIdx, right);
        }
    }

}
