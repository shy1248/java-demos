/**
 * @Date        : 2021-02-18 18:29:17
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 使用递归模拟查找迷宫路径
 *
 * map: 0 代表还未走过，1 代表墙，2 代表已走过，3代表已走过但不能通行
 *      入口为 map[6][1]，出口为 map[1][7]
 *
 *     1  1  1  1  1  1  1  1  1
 *     1  0  0  0  1  0  1  0  1
 *     1  0  1  0  0  1  1  0  1
 *     1  0  0  1  0  0  0  0  1
 *     1  1  0  0  1  0  1  1  1
 *     1  0  0  1  0  1  1  0  1
 *     1  0  1  0  0  0  1  0  1
 *     1  0  0  0  1  0  0  0  1
 *     1  1  1  1  1  1  1  1  1
 */
package me.shy.dsalgo.recursion;

import java.util.concurrent.TimeUnit;

public class Maze {
    // 迷宫地图
    int[][] map;
    // 记录部署
    int step = 0;

    public Maze() {
        // 初始化地图
        map = new int[][] { { 1, 1, 1, 1, 1, 1, 1, 1, 1 }, { 1, 0, 0, 0, 1, 0, 1, 0, 1 }, { 1, 0, 1, 0, 0, 1, 1, 0, 1 },
                { 1, 0, 0, 1, 0, 0, 0, 0, 1 }, { 1, 1, 0, 0, 1, 0, 1, 1, 1 }, { 1, 0, 0, 1, 0, 1, 1, 0, 1 },
                { 1, 0, 1, 0, 0, 0, 1, 0, 1 }, { 1, 0, 0, 0, 1, 0, 0, 0, 1 }, { 1, 1, 1, 1, 1, 1, 1, 1, 1 } };

    }

    /**
     * 递归搜索路径
     * 1.首先判断是否处于目的地，如果已经到达目的地，直接返回方法，结束递归，判断条件是目的地的坐标点为 2，表示已经走到该点；
     * 2.如果还未到达目的地，首先判断该点是否还未走过（值为0），如果已经走过（值为2或者3）或者是墙（值为1），则直接返回 false，表示不可通行；
     * 3.如果当前点未走过，先假定可以通行，并递归调用方法自身按照不同策略（方向的先后顺序）进行探测，若某个方向上可以通行（该方向上的下一个格子值为0），则对下一个
     *   格子继续递归调用方法自身按照不同策略（方向的先后顺序）进行探测...直到走到一个死地（该格子的四个方向上没有值为0的下一个格子）进行递归回溯，将其值改为3；
     * 4.注意不同的选路策略对最终走过的路径影响很大；
     * 5.由于数组是引用传递，因此每次递归产生的栈空间对其指向都是同一个，因此对其修改是生效的；
     * 6.计步器必须设计成成员属性才是同一份引用，如果是方法本地变量，则每次递归的栈空间独立拥有，不能进行全局计数；
     *
     * @param map 模拟地图的二维数组
     * @param i 地图的纵坐标
     * @param j 地图的横坐标
     * @return 如果该点可以走通或者已经到达目的地，返回 true，否则返回 false
     * @throws InterruptedException
     */
    public boolean searchWay(int[][] map, int i, int j) throws InterruptedException {
        step++;
        // 模拟搜索时间
        TimeUnit.MILLISECONDS.sleep(100);
        System.out.printf("Step[%s]: i=%s, j=%s ...\n", step, i, j);
        // printMap();

        // 已经找到出口
        if (map[1][7] == 2) {
            System.out.println("Done!");
            return true;
        } else {
            // 如果还未走过
            if (map[i][j] == 0) {
                // 假设可以通行，先标记
                map[i][j] = 2;

                // 选路策略：下 -> 左 -> 上 -> 右
                // 不同的策略所选择的路径不一样
                if (searchWay(map, i + 1, j)) { // 朝下走
                    return true;
                } else if (searchWay(map, i, j - 1)) { // 朝左走
                    return true;
                } else if (searchWay(map, i - 1, j)) { // 朝上走
                    return true;
                } else if (searchWay(map, i, j + 1)) { // 朝右走
                    return true;
                } else { // 该点不能走通，进行回溯标记
                    map[i][j] = 3;
                    return false;
                }
            } else {
                return false;
            }
        }
    }

    public void printMap() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                System.out.printf("%d  ", map[i][j]);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Maze maze = new Maze();
        try {
            maze.searchWay(maze.map, 6, 1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        maze.printMap();
    }
}
