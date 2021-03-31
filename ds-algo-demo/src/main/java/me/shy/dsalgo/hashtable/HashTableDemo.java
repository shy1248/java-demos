/**
 * @Date        : 2021-02-22 22:10:43
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 简单哈希表的实现
 */
package me.shy.dsalgo.hashtable;

public class HashTableDemo {
    public static void main(String[] args) {
        HashTable<Person> table = new HashTable<>(8);
        for (int i = 0; i < 20; i++) {
            Person person = new Person(i + 1, "Somebody" + (i + 1));
            table.add(person);
        }
        table.print();
    }
}

class HashTable<E> {
    private int size;
    private Object[] lists;

    public HashTable(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Illegal capacity: " + size);
        }
        this.size = size;
        // 初始化 HashTable
        lists = new Object[size];
        for (int i = 0; i < lists.length; i++) {
            lists[i] = new LinkList<E>();
        }
    }

    @SuppressWarnings("unchecked")
    public void print() {
        for (int i = 0; i < lists.length; i++) {
            LinkList<E> list = (LinkList<E>) lists[i];
            if (!list.isEmpty()) {
                list.print();
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void add(E element) {
        LinkList<E> list = (LinkList<E>) lists[hash(element)];
        list.add(new Node<E>(element));
    }

    /**
     * 取模实现的简单的 hash 函数
     * @param element
     * @return
     */
    private int hash(E element) {
        return element.hashCode() % size;
    }

}

class LinkList<E> {
    private Node<E> head;

    public boolean isEmpty() {
        return head == null;
    }

    /**
     * 添加节点
     * @param node 待添加的节点
     */
    public void add(Node<E> node) {
        if (null == head) {
            head = node;
        } else {
            Node<E> current = head;
            while (true) {
                if (null == current.next) {
                    break;
                }
                current = current.next;
            }
            current.next = node;
        }
    }

    public E get(E payload) {
        if (null == head) {
            System.out.println("The empty list.");
            return null;
        }

        Node<E> current = head;
        while (true) {
            if (payload.equals(current.getPayload()) || null == current.next) {
                break;
            }
            current = current.next;
        }
        return current.next == null ? null : current.getPayload();
    }

    public void print() {
        if (null == head) {
            System.out.println("The empty list.");
        } else {
            Node<E> current = head;
            while (true) {
                if (null == current.next) {
                    break;
                }
                System.out.printf("%s\n", current.getPayload());
                current = current.next;
            }
        }
    }
}

class Node<E> {
    private E payload;
    Node<E> next;

    public Node(E payload) {
        this.payload = payload;
    }

    public E getPayload() {
        return payload;
    }

    public void setPayload(E payload) {
        this.payload = payload;
    }
}

class Person {
    int id;
    String name;

    public Person(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person [id=" + id + ", name=" + name + "]";
    }
}
