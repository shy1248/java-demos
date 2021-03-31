/**
 * @Date        : 2021-02-19 10:18:50
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 八皇后问题解法
 *
 * 在8×8格的国际象棋上摆放8个皇后，使其不能互相攻击，即任意两个皇后都不能处于同一行、同一列或同一斜线上，问有多少种摆法。
 *
 * 采用递归回溯算法实现：
 * 1.先将第一个皇后放在棋盘的第一行的第一列保持不变；
 * 2.将二个皇后放在第二行的第一列，判断是否冲突，如果冲突，就将第二个皇后放置于第二行的第而列，再次判断是否冲突...依次放置判断，直到第二个皇后放好；
 * 3.剩余的六个皇后和第二个皇后一样，当8个皇后全部放好之后，第一个皇后位于第一行第一列的解法已全部求出，将第一个皇后摆放到第一行的第二列，然后按照
 *   先前的方式尝试摆放剩余7个皇后，直到第一个皇后走完第一行的所有列为止。
 *
 * 棋盘可以使用8行8列的二维数组模拟，但是由题目要求可知，在每一行上只能摆放一个皇后，其实相当于线性的，故采用一维数组来存放每一个正确的解法。
 * 数组的下标 n 代表第 n-1（数组索引从 0 开始）个皇后，也表示是放置在棋盘上的第 n-1 行，数组的值 array[n] 表示第 n-1 个皇后放置于棋盘的 n-1 行，array[n] 列；
 * 使用一维数组还有一个好处是避免的不同的皇后放在棋盘的同一行上，判断是否冲突时就只需要判断是否在同一列或者同一斜线上。
 *
 */
package me.shy.dsalgo.recursion;

public class EightQueens {
    // 代表总皇后的数量
    private int max = 8;
    // 代表着一种解法
    private int[] array = new int[max];
    // 计数总解法
    private int counter;
    // 计数回溯步数
    private int steps;

    /**
     * @param n 从 0 开始，代表着第 n-1 个皇后，也即棋盘的第 n-1 行
     */
    public void tryPlaced(int n) {
        // 当第 8 个皇后摆放完毕，表示一种解法已求出，打印并计数
        if (n == max) {
            printArray();
            counter++;
            return;
        } else {
            // 将第 n-1 个皇后依次放置于 第 n-1 行的 0-7 列上，然后判断是否冲突
            // 如果冲突就换下一列，如果不冲突，则继续摆放第 n 个皇后
            for (int col = 0; col < max; col++) {
                array[n] = col;
                // 步数自增
                steps++;
                // 该列存在冲突，继续尝试下一列
                if (isDead(n)) {
                    continue;
                } else {
                    // 摆放下一个皇后
                    tryPlaced(n + 1);
                }
            }
        }
    }

    /**
     * 检查是否冲突
     *
     * 当摆放第 n-1 个皇后时，需要检查其和前面所有已摆放的皇后是否在同一列和同一斜线上（不需要检查是否在同一行，因为每行只有一个皇后）
     * @param n 从 0 开始，代表棋盘上第 n-1 行，也即第 n-1 个皇后
     * @return
     */
    public boolean isDead(int n) {
        // 循环和前面所有已摆放的皇后进行检查
        for (int i = 0; i < n; i++) {
            // array[n] 表示的列数，array[n] == array[i] 即可判断是否处于同一列
            // Math.abs(n - i) == Math.abs(array[n] - array[i]，斜率判断
            // 类似于坐标系中 y=x 的一条直线,如果 abs(y2 - y1) == abs(x2 - x1)，说明斜率为 1，肯定在对角线上
            if (array[n] == array[i] || Math.abs(n - i) == Math.abs(array[n] - array[i])) {
                return true;
            }
        }
        return false;
    }

    public void printArray() {
        for (int i = 0; i < array.length; i++) {
            System.out.printf("%d ", array[i]);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        EightQueens eightQueens = new EightQueens();
        eightQueens.tryPlaced(0);
        System.out.println("Total answers is: " + eightQueens.counter);
        System.out.println("Total steps is: " + eightQueens.steps);
    }
}
