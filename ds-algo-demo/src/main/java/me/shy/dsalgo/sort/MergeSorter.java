/**
 * @Date        : 2021-02-19 22:54:52
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 归并排序
 */
package me.shy.dsalgo.sort;

public class MergeSorter<E extends Comparable<? super E>> extends AbstractSorter<E> {

    @Override
    void sort(E[] array) {
        // 创建临时数组，其大小与原始数组一致
        Object[] temp = new Object[array.length];
        mergeSort(array, 0, array.length - 1, temp);
    }

    /**
     * 归并排序的递归拆分以及调用合并的方法
     * @param <E>
     * @param array
     * @param left
     * @param right
     * @param temp
     */
    private void mergeSort(E[] array, int left, int right, Object[] temp) {
        // 只要还没有拆成一个元素为一个逻辑数组，就要继续递归
        // 如果拆成了一个元素一个逻辑数组，left == right
        if (left < right) {
            // 找出中间位置的索引
            int mid = (left + right) / 2;
            // 对左边的元素递归拆分，左边逻辑数组的下边界为 left，上边界为 mid
            mergeSort(array, left, mid, temp);
            // 对右边的逻辑数组进行递归拆分，右边逻辑数组的下边界为 mid+1，上边界为 right
            mergeSort(array, mid + 1, right, temp);
            // 递归拆分完毕，开始调用合并的方法
            merge(array, left, mid, right, temp);
        }
    }


    /**
     * 归并排序合并阶段
     * 将拆分为左右两边的两个数组合并到临时数组中
     * 归并排序采用递归方式将原始数组不断拆分（逻辑上），直到每个逻辑数组中只有一个元素后就开始合并，
     * 合并方式是将每两个数组按照排序顺序拷贝到临时数组中，其方式是：
     * 1.只要左右两个逻辑数组均不为空，循环的拿出左边数组的一个元素和右边数组的一个元素进行比较；
     * 如果左边元素较小（由小到大的顺序），就将其插入临时数组中，此时左边数组和临时数组的索引加一；
     * 反之，如果右边数组的元素较小，将其插入临时数组中，此时右边数组和临时数组的索引加一；
     * 注意，此时较大的一方数组的索引不变，下次循环时会将其与前一次较小的数组中的下一个值进行比较；
     * 重复以上步骤，直到左右两边某个数组为空（即索引已经指向上边界）；
     * 2.将剩下还有元素的数组中的元素依次插入至临时数组中；
     * 3.将临时数组中值拷贝回原始数组；
     * 4.该过程为递归调用，总共需合并的次数为数组元素减去 1
     *
     * @param <E>
     * @param array
     * @param left
     * @param mid
     * @param right
     * @param temp
     */
    @SuppressWarnings("unchecked")
    private void merge(E[] array, int left, int mid, int right, Object[] temp) {
        // 左边逻辑数组的索引的初始值，其上边界为 mid
        int leftInx = left;
        // 右边逻辑数组的初始值，其上边界为 right
        int rightInx = mid + 1;
        // 临时数组的索引，初始值为 0
        int tempInx = 0;

        // 当左右两边逻辑数组均有元素时（索引均为达到上边界），循环拿出2个数组的元素进行比较
        // 将较小的一方的元素插入临时数组，同时递增将其索引和临时数组索引往后移
        while (leftInx <= mid && rightInx <= right) {
            if (array[leftInx].compareTo(array[rightInx]) == -1) {
                temp[tempInx] = array[leftInx];
                leftInx += 1;
                tempInx += 1;
            } else {
                temp[tempInx] = array[rightInx];
                rightInx += 1;
                tempInx += 1;
            }
        }

        // 当左右两边数组某一边没有元素时，无需比较，只需将剩余还有元素的数组中元素依次按序插入临时数组即可
        // 这个有可能是左边逻辑数组，也要可能是右边的临时数组，无法确定，但不可能是同时的（否则不会退出前面的循环）均要进行处理
        while (leftInx <= mid) {
            temp[tempInx] = array[leftInx];
            leftInx += 1;
            tempInx += 1;
        }
        while (rightInx <= right) {
            temp[tempInx] = array[rightInx];
            rightInx += 1;
            tempInx += 1;
        }

        // 最后循环临时数组，将其值拷贝回原始数组
        tempInx = 0;
        for (int i = left; i <= right; i++) {
            array[i] = (E)temp[tempInx];
            tempInx += 1;
        }
    }
}
