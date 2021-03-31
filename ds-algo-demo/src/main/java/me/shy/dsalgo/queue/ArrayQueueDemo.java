/**
 * @Date        : 2021-02-15 16:17:15
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 队列模拟，使用数组才存储元素，该版本中数组只能使用一次，也即当队列加满后再出队，此时即使队列为空，也不能再添加元素进去
 */
package me.shy.dsalgo.queue;

import java.util.Scanner;

public class ArrayQueueDemo {
    public static void main(String[] args) {
        ArrayQueue<Integer> queue = new ArrayQueue<>(3);
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
            if("".equals((cmd + "").trim())){
                continue;
            }

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

class ArrayQueue<E> {
    // 队列的最大长度
    private int maxSize;
    // 队列的头部指针，指向队列头部的元素
    private int front;
    // 队列的尾部指针，指向队列尾部下一个元素的位置
    private int rear;
    // 用于存放元素的数组
    private transient Object[] array;

    /**
     * 构造方法
     * @param maxSize 队列的容量
     */
    public ArrayQueue(int maxSize) {
        this.maxSize = maxSize;
        if (this.maxSize <= 0) {
            throw new IllegalArgumentException("Illegal capacity: " + maxSize);
        }
        this.array = new Object[maxSize];
    }

    /**
     * 判断队列是否已满
     * @return
     */
    public boolean isFull() {
        return this.rear == this.maxSize;
    }

    /**
     * 判断队列是否为空
     * @return
     */
    public boolean isEmpty() {
        return this.front == this.rear;
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
        this.front++;
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
        this.rear++;
    }

    /**
     * 列出队列中元素，包括空元素
     */
    public void listQueue() {
        // 检查队列是否为空
        if (this.isEmpty()) {
            throw new RuntimeException("Queue is empty.");
        }
        // 只打印出有效的元素
        for (int i = this.front; i < this.rear; i++) {
            System.out.printf("E[%d=%s]\t", i, this.array[i]);
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
        return (this.rear - this.front);
    }
}
