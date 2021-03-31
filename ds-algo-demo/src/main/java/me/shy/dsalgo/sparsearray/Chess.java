/**
 * @Date        : 2021-02-15 14:24:20
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 五子棋存盘模拟
 *
 * 五子棋棋盘的长度，11列
 * 五子棋棋盘的宽度，11行
 * 五子棋棋盘表示的二维数组
 * 0表示无棋子，1表示黑子，2表示蓝子
 *
 * 普通数组转化为稀疏数组的过程如下：
 *
 *              chessArray                                sparseArray
 *
 *     0  0  0  0  0  0  0  0  0  0  0
 *     0  0  1  0  0  0  0  0  0  0  0
 *     0  0  0  2  0  0  0  0  0  0  0
 *     0  0  0  0  0  0  0  0  0  0  0            ind    row    col    val
 *     0  0  0  0  0  0  0  0  0  0  0            0      11     11     2         --> chessArray总共有11行11列，有2个有效值
 *     0  0  0  0  0  0  0  0  0  0  0    <==>    1      1      2      1         --> chessArray第2行第3列，有效值为1，代表一颗黑子
 *     0  0  0  0  0  0  0  0  0  0  0            2      2      3      2         --> chessArray第3行第4列，有效值为2，代表一颗蓝子
 *     0  0  0  0  0  0  0  0  0  0  0
 *     0  0  0  0  0  0  0  0  0  0  0
 *     0  0  0  0  0  0  0  0  0  0  0
 *     0  0  0  0  0  0  0  0  0  0  0
 *
 * 通过转化后将原来 11x11 的数组转化为 3x3 的数组，当需要将五子棋的装台系列化时可提高速度；
 * 适用于多维数组中存在大量无效数据的情况；
 *
 */
package me.shy.dsalgo.sparsearray;

public class Chess {

    /**
     * 初始化五子棋棋盘
     * @param length 棋盘的行数
     * @param width 棋盘的列数
     * @return
     */
    public int[][] initChessArray(int length, int width) {
        int[][] chessArray = new int[length][width];
        // 第2行第3列一颗黑子
        chessArray[1][2] = 1;
        // 第3行第4列一颗蓝子
        chessArray[2][3] = 2;
        chessArray[4][5] = 2;
        return chessArray;
    }

    /**
     * 计算普通二维数组的有效值个数
     *
     * @param array
     * @param invalid 非有效值
     * @return
     */
    public int getValidSize(int[][] array, int invalid) {
        int validSzie = 0;
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                int value = array[i][j];
                if (value != invalid) {
                    validSzie++;
                }
            }
        }
        return validSzie;
    }

    /**
     * 将普通二维数组转为稀疏数组
     * 1.计算普通二维数组中的有效值的个数；
     * 2.有效值的个数加上1（第一行为元数据信息）即为稀疏数组的行数；
     * 3.对于二维数组，稀疏数组的列数为3，（行，列，值）
     * 4.从第二行开始记录原始数组中的行、列、值信息
     * @param invalid 非有效值
     * @return
     */
    public int[][] toSparseArray(int[][] array, int invalid) {
        int validSize = this.getValidSize(array, invalid);
        int rowNumber = validSize + 1;
        int[][] sparseArray = new int[rowNumber][3];
        // 初始化稀疏数组的第一行
        sparseArray[0] = new int[] { array.length, array[0].length, validSize };

        // 初始化稀疏数组中其它行信息
        int validCounter = 0;
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                int value = array[i][j];
                // 每次碰到一个有效值，就用原始数组有效值的行、列和值组成数组赋给稀疏数组
                if (value != invalid) {
                    validCounter++;
                    sparseArray[validCounter] = new int[] { i, j, value };
                }
            }
        }

        return sparseArray;
    }

    /**
     * 从稀疏数组恢复为普通数组
     * 1.读取稀疏数组的第一行，第一行第一列为原始数组的行数，第一行的第二列为原始数组的列数；
     * 2.根据读取的信息创建一个新的数组；
     * 3.从稀疏数组的第二行开始，一次读取并填充原始数组；
     * @param array
     * @param invalid 非有效值填充
     * @return
     */
    public int[][] fromSparseArray(int[][] array, int invalid) {
        if (array.length < 0 || array[0].length != 3) {
            throw new RuntimeException("Invalid sparse array!");
        }
        // 读取原始数组的行列信息
        int origArrayLength = array[0][0];
        int origArrayWidth = array[0][1];
        // 初始化新数组
        int[][] origArray = new int[origArrayLength][origArrayWidth];
        // 根据稀疏数组的其他行（除去第一行）信息填充新数组
        for (int i = 1; i < array.length; i++) {
            origArray[array[i][0]][array[i][1]] = array[i][2];
        }
        return origArray;
    }

    /**
     * 格式化打印数组
     * @param array
     */
    public void out(int[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                System.out.printf("%d  ", array[i][j]);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Chess chess = new Chess();
        // 初始化五子棋棋盘，11行11列
        int[][] chessArray = chess.initChessArray(11, 11);
        System.out.println("原始数组为：");
        chess.out(chessArray);
        // 转化为稀疏数组并输出
        int[][] sparseArray = chess.toSparseArray(chessArray, 0);
        System.out.println("从普通数组转为稀疏数组：");
        chess.out(sparseArray);
        // 从稀疏数组转为普通数组并输出
        int[][] origArray = chess.fromSparseArray(sparseArray, 0);
        System.out.println("从稀疏数组转为普通数组：");
        chess.out(origArray);
    }
}
