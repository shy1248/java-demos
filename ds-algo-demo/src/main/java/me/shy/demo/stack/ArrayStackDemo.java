/**
 * @Date        : 2021-02-17 11:06:42
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 使用数组模拟栈结构
 */
package me.shy.demo.stack;

public class ArrayStackDemo {
    public static void main(String[] args) {
        // created
        ArrayStack<Integer> numbers = new ArrayStack<>(5);
        System.out.println("Original elements is: ");
        numbers.print();
        // push
        for (int i = 1; i <= 5; i++) {
            System.out.printf("[PUSH] element: %d\n", i);
            numbers.push(i);
        }
        System.out.println("Now elements is: ");
        numbers.print();
        // can not push
        // numbers.push(6);
        // numbers.print();

        // peek
        System.out.printf("[PEEK] the first element is: %d\n", numbers.peek());
        System.out.println("Now elements is: ");
        numbers.print();

        // pop
        for (int i = 1; i <= 5; i++) {
            int number = numbers.pop();
            System.out.printf("[POP] the %d times poped element is: %d.\n", i, number);
        }
        System.out.println("Now elements is: ");
        numbers.print();
        // can not pop
        // numbers.pop();
    }
}

class ArrayStack<E> {
    // 栈的容量
    private int capacity;
    // 栈顶指针，默认 -1 ，表示栈此时为空
    private int top = -1;
    // 栈存放元素的数组
    private Object[] elements;

    public ArrayStack(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("Illegal capacity: " + capacity);
        }
        this.capacity = capacity;
        elements = new Object[capacity];
    }

    /**
     * 检查是否为空栈
     * @return 如果栈为空，返回 true；否则返回 false
     */
    public boolean isEmpty() {
        return top == -1;
    }

    /**
     * 检查栈是否已满
     * @return 如果栈已满，返回 true；否则返回 false
     */
    public boolean isFull() {
        return top == capacity - 1;
    }

    /**
     * 入栈操作，如果栈已满，则抛出异常
     * @param element 待入栈的元素
     */
    public void push(E element) {
        if (isFull()) {
            throw new RuntimeException("Stack is full.");
        }
        // 先将栈顶指针往上移一位，然后将待入栈元素存储
        top++;
        elements[top] = element;
    }

    /**
     * 出栈操作
     * @return 如过栈为空，抛出异常；否则返回栈顶的第一个元素
     */
    @SuppressWarnings("unchecked")
    public E pop() {
        if (isEmpty()) {
            throw new RuntimeException("Stack is empty.");
        }
        // 首先获取栈顶第一个元素，然后将栈顶指针往下移动一位，最后返回之前获取的元素
        E element = (E) elements[top];
        top--;
        return element;
    }

    /**
     * 获取栈顶第一个元素，但是元素不出栈；如果栈为空，则抛出异常
     * @return
     */
    @SuppressWarnings("unchecked")
    public E peek() {
        if (isEmpty()) {
            throw new RuntimeException("Stack is empty.");
        }
        // 不移动栈顶指针，相当于元素不出栈，因此直接返回栈顶第一个元素
        return (E) elements[top];
    }

    /**
     * 打印栈的元素，注意需要从栈顶开始
     */
    public void print() {
        if (isEmpty()) {
            System.out.println("Stack is empty.");
        }
        for (int i = top; i >= 0; i--) {
            System.out.println(elements[i]);
        }
    }
}
