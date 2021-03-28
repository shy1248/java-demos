/**
 * @Date        : 2021-02-15 16:17:15
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 队列模拟，使用数组才存储元素，该版本使用环形数组来模拟队列，解决数组只能使用一次的问题
 *
 * 环形数组：当队列尾部加满元素后，如果队列前面出队了元素空出了位置，就将新加的元素继续往数组前面空出的位置上新增元素，
 * 覆盖已出队的元素，即在逻辑上形成一个环。
 * 为了实现循环的效果，在队列相关的指针更新时必须使用队列的队列头部和尾部的指针与数组的 maxsize 值进行取模运行。
 *
 * 环形队列存储的有效元素个数的计算公式为： (rear + maxsize - front) % maxsize
 *
 */
package me.shy.demo.queue;

import java.util.Scanner;

public class CycleArrayQueueDemo {
    public static void main(String[] args) {
        CycleArrayQueue<Integer> queue = new CycleArrayQueue<>(4);
        char cmd = ' ';
        Scanner in = new Scanner(System.in);
        boolean loop = true;
        while (loop) {
            System.err.println(
                    "############################################################################################################");
            System.out.println(
                    "# Please input a command:                                                                                  #");
            System.out.printf(
                    "# All suport commands is: (l): List queue\t(d): Dequeue\t(i): Inqueue\t(h): Head queue\t(e): Exit  #");
            System.out.println();
            System.err.println(
                    "############################################################################################################");
            System.out.print("> ");
            cmd = in.next().charAt(0);

            try {
                switch (cmd) {
                    case 'l':
                        queue.listQueue();
                        break;
                    case 'd':
                        int number = Integer.valueOf(queue.deQueue());
                        System.out.println("Get value is: " + number);
                        break;
                    case 'i':
                        System.out.println("Please input a number: ");
                        System.out.print("> ");
                        int input = in.nextInt();
                        queue.inQueue(Integer.valueOf(input));
                        System.out.println(String.format("Element %d is in queue.", input));
                        break;
                    case 'h':
                        System.out.println("Head number is: " + queue.head());
                        break;
                    case 'e':
                        in.close();
                        loop = false;
                        System.out.println("Exit!");
                        break;
                    default:
                        System.out.println("Invalid command: " + cmd);
                        break;
                }
            } catch (RuntimeException e) {
                System.err.println(e.getMessage());
            }
            System.out.println();
        }
    }
}

class CycleArrayQueue<E> {
    // 队列的最大长度
    private int maxSize;
    // 队列的头部指针，指向队列头部的元素
    private int front;
    // 队列的尾部指针，指向队列尾部的下一个元素的位置
    private int rear;
    // 用于存放元素的数组
    private transient Object[] array;

    /**
     * 构造方法
     * @param maxSize 队列的容量
     */
    public CycleArrayQueue(int maxSize) {
        // 队列尾部会预留一个空间不存放有效数据，因此实际上的最大容量为此值减去1
        this.maxSize = maxSize;
        if (this.maxSize <= 1) {
            throw new IllegalArgumentException("Illegal capacity: " + maxSize);
        }
        this.array = new Object[maxSize];
    }

    /**
     * 判断队列是否已满
     * @return
     */
    public boolean isFull() {
        // 方式1
        // 环形队列判断队列是否已满的方法
        // front 为当前队列头部的元素，rear 为当前队列尾部的元素，+1 即表示下一个元素应该存放的位置
        // 如果与 maxsize 取模后位置重叠，则表示对列已满
        // return (this.rear + 1) % this.maxSize == this.front;

        // 方式2
        // 如果队列中有效元素的个数与数组的容量相等，即表示队列已满
        return this.queueSize() == this.maxSize - 1;
    }

    /**
     * 判断队列是否为空
     * @return
     */
    public boolean isEmpty() {
        // 方式 1
        // return this.front == this.rear;

        // 方式 2
        // 如果当前队列的有效元素个数为0，即表示队列已空
        return this.queueSize() == 0;
    }

    /**
     * 出队操作
     * @return
     */
    @SuppressWarnings("unchecked")
    public E deQueue() {
        // 检查队列是否为空
        if (this.isEmpty()) {
            throw new RuntimeException("Queue is empty.");
        }
        // 取出元素
        E element = (E) array[this.front];
        // 将队头指针后移
        this.front = (this.front + 1) % this.maxSize;
        return element;
    }

    /**
     * 入队操作
     * @param element
     */
    public void inQueue(E element) {
        // 检查队列是否已满
        if (this.isFull()) {
            throw new RuntimeException("Queue is full.");
        }
        // 存储元素
        array[this.rear] = element;
        // 变更队列尾部的指针
        this.rear = (this.rear + 1) % this.maxSize;
    }

    /**
     * 列出队列中元素，包括空元素
     */
    public void listQueue() {
        // 检查队列是否为空
        if (this.isEmpty()) {
            throw new RuntimeException("Queue is empty.");
        }
        // 从队列头部开始打印，一直打印到有效元素个
        // 注意，环形数组有可能回到队列尾部，因此需要取模运算
        for (int i = this.front; i < this.front + this.queueSize(); i++) {
            System.out.printf("E[%d]=%s\t", i % this.maxSize, this.array[i % this.maxSize]);
        }
        System.out.println(String.format(", total elements number is: %s", this.queueSize()));
    }

    /**
     * 显示队列头部元素，但不出队
     * @return
     */
    @SuppressWarnings("unchecked")
    public E head() {
        // 检查队列是否为空
        if (this.isEmpty()) {
            throw new RuntimeException("Queue is empty.");
        }
        // 直接返回元素，但不改变队列尾部指针位置，相当于只显示而不取出元素
        return (E) this.array[this.front];
    }

    /**
     * 获取当前队列中的有效元素个数
     * @return
     */
    public int queueSize() {
        return (this.rear + this.maxSize - this.front) % this.maxSize;
    }
}
