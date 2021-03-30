/**
 * @Date        : 2021-02-16 11:14:47
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 单链表面试题
 *
 * 链表节点使用了迭代器模式，遍历起来比较方便
 *
 * 1. 求单链表中有效节点的个数（新浪）；
 * 2. 查找单链表中倒数第k个节点（新浪）；
 * 3. 单链表的反转（腾讯）；
 * 4. 从尾到头打印单链表（百度，要求：方式1：反向遍历；方式2：栈）；
 * 5. 合并2个有序的单链表，合并后依然有序；
 *
 */
package me.shy.demo.list;

import java.util.Stack;

public class UnidirectionalLinkedListInterviewDemo {
    public static void main(String[] args) {
        LinkedList<Integer> oddNumers = new LinkedList<>();
        LinkedList<Integer> evenNumbers = new LinkedList<>();

        for (int i = 9; i >= 0; i--) {
            Node<Integer> node = new Node<>(i);
            if (i % 2 != 0) {
                oddNumers.add(node);
                // oddNumers.orderedInsert(node);
            } else {
                evenNumbers.orderedInsert(node);

            }
        }

        oddNumers.print();
        oddNumers.reversedPrintByStack();
        evenNumbers.print();
        evenNumbers.reversedPrintByRecursived(evenNumbers.head, 0);

        oddNumers.resversedOnSelf();
        oddNumers.print();


        evenNumbers.orderedMerge(oddNumers);
        evenNumbers.print();

        evenNumbers.resversedByNewHead();
        evenNumbers.print();

        // System.out.println(evenNumbers.lastIndexOf(2));   // Node [data=6]
        // System.out.println(evenNumbers.lastIndexOf(5));   // Node [data=0]
        // System.out.println(evenNumbers.lastIndexOf(6));   // null
        // System.out.println(evenNumbers.lastIndexOf(-1)); // null

        // Node<Person> person2 = new Node<>(new Person(2));
        // Node<Person> person1 = new Node<>(new Person(1));
        // LinkedList<Person> persons = new LinkedList<>();
        // persons.orderedInsert(person2);
        // persons.orderedInsert(person1);
        // assert oddNumers.getSize() == list.size;
    }
}

/**
 * 单向链表
 * @param <T>
 */
class LinkedList<T> {
    // 单向链表的头节点，不存储数据
    Node<T> head;
    // 单向链表除头节点以外的节点数，每次添加节点的时候自增
    // 冗余数据，如果没有该记录，则每次需要获取节点数的时候都需要遍历
    int size;

    public LinkedList() {
        head = new Node<T>(null);
    }

    /**
     * 添加新节点到链表尾部：
     * 从头节点开始遍历链表，找到最后一个节点，然后将最后一个节点的 next 指向待加入的节点
     * 然后自增 size 属性
     *
     * @param node
     */
    public void add(Node<T> node) {
        Node<T> curNode = head;
        // 只要当前节点的下一个节点不为空，表示还没有到链表的尾部，就将当前节点指向当前节点的下一个节点进行迭代
        while (curNode.hasNext()) {
            curNode = curNode.next();
        }
        // 遍历到链表尾节点，将其 next 指向待添加的节点
        curNode.nextNode = node;
        // 链表 size 属性自增
        size++;
    }

    /**
     * 按照 Node 的顺序插入
     * 首先遍历待插入节点的位置，然后将待插入的节点的下一个节点指向当前节点的下一个节点，将当前节点的下一个节点指向待插入的节点
     * 然后自增 size 属性
     *
     * @param node
     */
    public void orderedInsert(Node<T> node) {
        Node<T> curNode = head;
        // 迭代查询待插入的位置
        while (curNode.hasNext()) {
            // 因为是单向链表，必须用当前节点的下一个节点和待插入的节点进行比较，否则无法插入
            // 当前节点的下一个节点大于待插入的节点，停止迭代
            if (1 == curNode.nextNode.compareTo(node.data)) {
                break;
            }
            curNode = curNode.next();
        }
        // 找到待插入的位置，将待插入的节点的下一个节点指向当前节点的下一个节点
        node.nextNode = curNode.nextNode;
        // 并将当前节点的下一个节点指向待插入的节点
        curNode.nextNode = node;
        // 链表 size 属性自增
        size++;
    }

    /**
    * 判断链表是否为空
    * @return
    */
    public boolean isEmpty() {
        // return head.next == null;
        // return head.hasNext == false;
        return size == 0;
    }

    /**
     * 输出链表
     */
    public void print() {
        // 如果链表为空，打印提示并返回
        if (this.isEmpty()) {
            System.out.println("List is empty.");
            return;
        }
        // 打印
        int counter = 0;
        Node<T> curNode = head;
        while (curNode.hasNext()) {
            curNode = curNode.next();
            counter++;
            if (counter == size) {
                // 最后一个节点
                System.out.printf("%s\n", curNode);
            } else {
                // 非最后一个节点
                System.out.printf("%s -> ", curNode);
            }
        }
    }

    /**
    * 1. 求单链表中有效节点的个数
    * 迭代计算链表中的节点数
    * @return 节点数
    */
    public int getSize() {
        // 如果记录了 size 属性
        // return size;

        // 没有记录size属性，通过迭代计算
        int counter = 0;
        if (null == head.next()) {
            return 0;
        } else {
            Node<T> curNode = head;
            while (curNode.hasNext()) {
                counter++;
                curNode = curNode.next();
            }
            return counter;
        }
    }

    /**
     * 2. 查找单链表中倒数第k个节点（新浪）
     * 获取链表中的倒数第 index 个节点：
     * 首先获取单向链表的总节点数 size，那么倒数第 index 个节点即为正向的第 size - index 的 nextNode
     *
     * @param index 需要查找的倒数的节点索引
     * @return 如果查找到满足条件的 Node，返回该 Node；否则返回 null
     */
    public Node<T> lastIndexOf(int index) {
        // 如果链表为空，直接返回 null
        if (this.isEmpty()) {
            return null;
        }
        // 如果给定的倒数索引 index 为负数或者超过单向链表的总节点数，非法，返回 null
        if (index < 0 || index > size) {
            return null;
        }
        // 迭代查找
        int counter = 0;
        Node<T> curNode = head;
        while (curNode.hasNext()) {
            if (counter == size - index) {
                return curNode.next();
            }
            curNode = curNode.next();
            counter++;
        }

        return null;
    }

    /**
     * 3. 单链表的反转（腾讯）
     * 采用正向遍历，头插法实现：
     * 方式一：迭代原始链表的第一个节点实例的下一个节点，将其插入至 head 节点之后，同时改变原始节点的一下个节点为下下一个节点
     *   f = firstNode    n = firstNode.nextNode    t = temp
     *
     *  ---------------------------------      -------------------------
     *  |          f    n               |      |          f   n        |
     *  |  head -> 1 -> 2 -> 3 -> 4     |  ==> |  head -> 1 -> 3 -> 4  |   ==>
     *  |                               |      |             /         |
     *  |                               |      |           2           |
     *  |                               |      |           t           |
     *  |-------------------------------|      |-----------------------|
     *
     *   ---------------------------------      -------------------------
     *  |          f    n               |      |           f   n        |
     *  |  head -> 1 -> 3 -> -> 4       |  ==> |  head     1 -> 3 -> 4  |   ==>
     *  |         /                     |      |       \  /             |
     *  |        2                      |      |        2               |
     *  |        t                      |      |        t               |
     *  |-------------------------------|      |------------------------|
     *
     *  ---------------------------------      -------------------------
     *  |               f    n          |      |               f    n  |
     *  |  head -> 2 -> 1 -> 3 -> 4     |  ==> |  head -> 2 -> 1 -> 4  |   ==>
     *  |                               |      |             /         |
     *  |                               |      |           3           |
     *  |                               |      |           t           |
     *  |-------------------------------|      |-----------------------|
     *
     *  ---------------------------------      -------------------------
     *  |               f    n          |      |                f    n  |
     *  |  head -> 2 -> 1 -> 4          |  ==> |  head     2 -> 1 -> 4  |   ==>
     *  |         /                     |      |       \  /             |
     *  |        3                      |      |        3               |
     *  |        t                      |      |        t               |
     *  |-------------------------------|      |------------------------|
     *
     * @return 反转后的链表
     */
    public void resversedOnSelf() {
        // 如果单向链表为空或者只有一个节点，则无需反转，直接返回
        if (this.isEmpty() || size == 1) {
            return;
        }
        // fisrtNode 永远是原始链表的第一个节点对象
        // 由于采用头插法，因此随着迭代的进行，其位置会不但后移，直到其变成最后一个节点
        Node<T> firstNode = this.head.next();
        // 辅助迭代节点，总是指向 fistNode 的下一个节点
        Node<T> temp = null;
        while (firstNode.hasNext()) {
            // 临时保存 firstNode 的下一个节点
            temp = firstNode.next();
            // 变更 firstNode 的下一个节点为当前下一个节点的再下一个节点
            firstNode.nextNode = temp.next();
            // 将临时保存的下一个节点的后一个节点指向 head 节点的之后节点
            temp.nextNode = head.nextNode;
            // 将 head 节点的下一个节点变更为临时保存的节点
            head.nextNode = temp;
        }
    }

    /**
     * 3. 单链表的反转（腾讯）
     * 方式二：先创建一个 reversedHeadNode 对象，然后将 curNode 插入到 reversedHeadNode 之后，最后将 head 的 nextNode 指向 reversedHeadNode 的 nextNode
     */
    public void resversedByNewHead() {
        // 如果单向链表为空或者只有一个节点，则无需反转，直接返回
        if (this.isEmpty() || size == 1) {
            return;
        }
        // 创建一个新的头节点
        Node<T> reversedHead = new Node<>(null);
        // 临时迭代节点
        Node<T> curNode = head.next();
        // 临时存储当前节点的下一个节点
        Node<T> temp = null;
        // for (int i = 0; i < size; i++) {
        while(null != curNode) {
            // 保存当前节点，因为后面要修改当前节点下一个节点为新头节点的下一个节点
            // 如果不保存，迭代一次后就找不到下一个应该迭代的节点了
            temp = curNode.next();
            // 将当前节点的下一个节点指向新的头节点的下一个节点
            curNode.nextNode = reversedHead.nextNode;
            // 新的头节点的下一个节点指向当前节点，完成头部插入
            reversedHead.nextNode = curNode;
            // 迭代当前节点
            curNode = temp;
        }
        // 迭代完毕，将当前节点的头节点指向新的头节点的下一个节点，完成头节点的切换
        // head.nextNode = reversedHead.nextNode;
        head = reversedHead;
    }

    /**
     * 4. 从尾到头打印单链表（百度，要求：方式1：反向遍历；方式2：栈）；
     * 使用栈的方式实现逆序打印，注意只是逆序打印，并不反转单向链表
     */
    public void reversedPrintByStack() {
        // 如果链表为空，打印提示并返回
        if (this.isEmpty()) {
            System.out.println("List is empty.");
            return;
        }
        // 遍历单向链表并压栈
        Stack<Node<T>> stack = new Stack<>();
        Node<T> curNode = head;
        while (curNode.hasNext()) {
            curNode = curNode.next();
            stack.push(curNode);
        }
        // 出栈打印
        int counter = 0;
        while (!stack.empty()) {
            Node<T> node = stack.pop();
            counter++;
            if (counter == size) {
                // 最后一个节点
                System.out.printf("%s\n", node);
            } else {
                // 非最后一个节点
                System.out.printf("%s -> ", node);
            }
        }
    }

    /**
     * 4. 从尾到头打印单链表（百度，要求：方式1：反向遍历；方式2：栈）；
     * 使用递归的方式实现逆序打印，注意只是逆序打印，并不反转单向链表
     * @param head 单链表的头节点
     */
    public void reversedPrintByRecursived(Node<T> head, int counter) {
        // 如果链表为空，打印提示并返回
        if (this.isEmpty()) {
            System.out.println("List is empty.");
            return;
        }

        Node<T> next = head.next();
        if (null == next) {
            return;
        } else {
            counter++;
            reversedPrintByRecursived(next, counter);
            // 因为是逆序打印，第一个节点会在最后输出
            if (counter == 1) {
                System.out.printf("%s\n", next);
            } else {
                System.out.printf("%s -> ", next);
            }
        }
    }

    /**
     * 5. 合并2个有序的单链表，合并后依然有序；
     * 注意：此处的原链表必须有序
     *
     * @param list
     * @return
     */
    public LinkedList<T> orderedMerge(LinkedList<T> list) {
        // 如果要合并的链表为空，直接返回本链表自身
        if (list.isEmpty()) {
            return this;
        }
        // 遍历待合并的链表
        Node<T> curNode = list.head.next();
        Node<T> temp = null;
        while (null != curNode) {
            // 保存当前节点的下一个节点
            temp = curNode.next();
            // 将当前节点有序插入到原链表中
            this.orderedInsert(curNode);
            // 迭代当前节点为下一个节点
            curNode = temp;
        }
        return this;
    }

}

/**
 * 单向链表的节点
 * 实现了自定义的 Iterable 接口，方便迭代；
 * 实现了 JDK 的 java.lang.Comparable 接口，用于元素排序；
 * 如果 Node 的类型 T 自身实现了 java.lang.Comparable 接口，则可比较，否则抛出异常
 * @param <T>
 */
class Node<T> implements Iterable<T>, Comparable<T>{
    T data;
    Node<T> nextNode;

    public Node(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Node [data=" + data + "]";
    }

    @Override
    public boolean hasNext() {
        return nextNode != null;
    }

    @Override
    public Node<T> next() {
        return nextNode;
    }

    /**
     * 根据 Node 的 T data 属性进行比较
     * 如果 T 未实现 java.lang.Comparable 接口，抛出异常
     */
    @SuppressWarnings("unchecked")
    @Override
    public int compareTo(T o) {
        if (o instanceof Comparable) {
            Comparable<T> c = (Comparable<T>) this.data;
            return c.compareTo(o);
        } else {
            throw new RuntimeException(String.format("Class %s is not a comparable instance.", o.getClass().getName()));
        }
    }
}

/**
 * 迭代器接口
 */
interface Iterable<T> {
    boolean hasNext();

    Iterable<T> next();
}

/**
 * 测试类，未实现 java.lang.Comparable 接口，当对 Person 类型的 Node 调用 orderedInsert 方法时抛出异常
 */
class Person {
    int id;

    public Person(int id) {
        this.id = id;
	}

}
