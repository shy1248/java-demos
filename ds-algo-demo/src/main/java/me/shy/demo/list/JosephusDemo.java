/**
 * @Date        : 2021-02-16 21:08:32
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 使用单向循环链表解决 Josephus 问题
 *
 * 有n个小孩围成一圈，给他们从1开始依次编号，从编号为1的小孩开始报数，数到第m个小孩出列，
 * 然后从出列的下一个小孩重新开始报数，数到第m个小孩又出列，…，如此反复直到所有的小孩全部出列为止，求整个出列序列。
 *
 * 如当 n=6，m=5 时的出列序列是 5，4，6，2，3，1
 *
 */
package me.shy.demo.list;

public class JosephusDemo {
    public static void main(String[] args) {
        CircularityBoys boys = new CircularityBoys();
        boys.linkedBoys(60);
        // boys.print();
        boys.outOfBoys(8, 9, 60);
    }
}

/**
 * 小孩围城一圈的单向循环链表
 */
class CircularityBoys {
    // 表示第一个小孩节点
    private Boy first;

    /**
     * 初始化小孩的循环链表
     * 1.由于没有头节点，因此 first 代表的第一个小孩为空；
     * 2.当向链表中添加第一个小孩的时候，初始化 first 节点，并将 fisrt 节点的下一个节点指向自身，形成一个但节点的环状结构；
     * 3.当待添加的节点不是第一个节点时，此时为了插入新节点并且保证插入节点后的链表依然是环状结构，需要保证第一个节点 first
     *   不动，因为待插入的新节点的下一个节点要指向它；
     * 4.引入辅助指针 currentBoy，该节点永远为单向链表的最后一个节点，将其下一个节点指向待插入的节点 boy，然后将待插入节点
     *   boy 的下一个节点执行第一个节点 first，最后将 currentBoy 指向待插入的节点 boy，即完成一次插入操作；
     *
     * @param boySize 小孩的数目
     */
    public void linkedBoys(int boySize) {
        // 如果小孩的数目小于1，抛出异常
        if (boySize < 1) {
            throw new IllegalArgumentException("Illlegal boy size: " + boySize);
        }

        // 通过循环往链表中添加小孩并形成环状结构
        // 辅助指针，代表当前的小孩，每次添加以为小孩后将其后移
        Boy currentBoy = null;
        for (int i = 1; i <= boySize; i++) {
            // 创建小孩节点
            Boy boy = new Boy(i);
            if (i == 1) {
                // 如果是第一个小孩节点就将该小孩赋给 first 属性
                first = boy;
                // 将 first 节点的下一个节点指向自身，形成单节点的环状结构
                first.setNext(first);
                // 将辅助指针指向第一个节点
                currentBoy = first;
            } else {
                // 如果不是第一个节点，就将辅助指针的节点的下一个节点指向新节点
                currentBoy.setNext(boy);
                // 将新节点的下一个节点指向第一个节点
                boy.setNext(first);
                // 将辅助指针后移
                currentBoy = boy;
            }
        }
    }

    /**
     * 打印输出
     */
    public void print() {
        // 没有加入任何小孩
        if (null == first) {
            System.out.println("No boys entered.");
            return;
        }
        // 循环输出
        Boy currentBoy = first;
        while (true) {
            System.out.println(currentBoy);
            // 如果当前小孩节点的下一个节点指向的是第一个节点，环状结构遍历完成
            if (currentBoy.getNext() == first) {
                break;
            }
            // 迭代小孩节点
            currentBoy = currentBoy.getNext();
        }
    }

    /**
     * 小孩出圈
     * 1.引入辅助指针 preNode，该节点的下一个节点永远为 first 节点；
     * 2.首先将 first 节点数到 start 处，此时 preNode 节点亦需要跟随移动；
     * 3.开始从 first 节点报数，由于 first 自身节点也需要报数，因此应该将 preNode 和 first 节点继续往前移动 util - 1 个位置；
     * 4.此时 first 节点指向的小孩需要出圈：继续将 fisrt 往前移动一位，将 preNode 的下一个节点重新指向 first，此时原来 first 节点即从
     *   单向循环链表中删除，代表小孩出圈；
     * 5.重复以上3、4两步，直到 preNode == first（代表圈中只剩下一个小孩）为止
     *
     * @param start 从第几个小孩开始
     * @param util 数到第几个小孩出圈
     * @param boySize 总共有多少个小孩
     */
    public void outOfBoys(int start, int util, int boySize) {
        // 将 first 移动至 start 的位置
        first = skipTo(first, start - 1);
        // 创建辅助指针 preNode，并将其移动至 first 节点的前一个节点的位置
        Boy preNode = first;
        preNode = skipTo(preNode, boySize - 1);
        // System.out.println(first);
        // System.out.println(preNode);

        // 开始小孩出圈操作
        int counter = 0;
        while (true) {
            counter++;
            // 圈中只剩下一个小孩，终止
            // if (preNode == first) {
            // 每次出圈一个，因此当计算器刚好大于小孩数量时，即将所有小孩出圈完毕
            if (counter > boySize) {
                preNode = first = null;
                break;
            }
            // 同时移动 first 和 preNode 至报数的步长位置，此时 first 指向的小孩要出圈
            first = skipTo(first, util - 1);
            preNode = skipTo(preNode, util - 1);
            // 出圈
            // 输出出圈小孩
            System.out.printf("The %s times, %s out circle.\n", counter, first);
            // 删除出圈小孩的节点
            first = first.getNext();
            preNode.setNext(first);

        }
    }

    /**
     * 将给定的小孩节点指针向前移动 n 位
     * @param boy 要移动小孩节点
     * @param n 需要移动的位数，为大于 0 的整数
     */
    private Boy skipTo(Boy boy, int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Illagal argument n: " + n);
        }
        for (int i = 0; i < n; i++) {
            boy = boy.getNext();
        }
        return boy;
    }
}

/**
 * 小孩节点
 */
class Boy {
    private int id;
    private Boy next;

    public Boy(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Boy [id=" + id + "]";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boy getNext() {
        return next;
    }

    public void setNext(Boy next) {
        this.next = next;
    }

}
