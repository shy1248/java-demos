/**
 * @Date        : 2021-02-27 14:23:25
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 二叉树
 */
package me.shy.dsalgo.tree;

public class BinaryTreeDemo {
    public static void main(String[] args) {

    }
}

/**
 * 二叉树
 * @param <E>
 */
class BinaryTree<E> {
    private Node<E> root;

    /**
     * 按照特定的次序输出二叉树
     * @param type 指定的次序，1为前序，2为中序，3为后序
     */
    public void print(int type) {
        if (type < 0 || type > 3) {
            throw new IllegalArgumentException(
                    "Invalid value of parameter type, must be a integer of [1-3], but excepted: " + type);
        }
        if (1 == type) {
            root.prefixPrint();
        } else if (2 == type) {
            root.infixPrint();
        } else {
            root.suffixPrint();
        }
    }

    public Node<E> getRoot() {
        return root;
    }

    public void setRoot(Node<E> root) {
        this.root = root;
    }

}

/**
 * 二叉树的节点
 */
class Node<E> {
    // 树的节点ID
    private int id = 1;
    // 树的节点的有效负载（数据）
    private E payload;
    // 左子树
    private Node<E> left;
    // 右子树
    private Node<E> right;

    /**
     * 前序输出
     */
    public void prefixPrint() {
        System.out.println(this);
        if (null != this.left) {
            this.left.prefixPrint();
        }
        if (null != this.right) {
            this.right.prefixPrint();
        }
    }

    /**
     * 中序输出
     */
    public void infixPrint() {
        if (null != this.left) {
            this.left.infixPrint();
        }
        System.out.println(this);
        if (null != this.right) {
            this.right.infixPrint();
        }
    }

    /**
     * 后序输出
     */
    public void suffixPrint() {
        if (null != this.left) {
            this.left.suffixPrint();
        }
        if (null != this.right) {
            this.right.suffixPrint();
        }
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "Node [id=" + id + ", payload=" + payload + "]";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public E getPayload() {
        return payload;
    }

    public void setPayload(E payload) {
        this.payload = payload;
    }

    public Node<E> getLeft() {
        return left;
    }

    public void setLeft(Node<E> left) {
        this.left = left;
    }

    public Node<E> getRight() {
        return right;
    }

    public void setRight(Node<E> right) {
        this.right = right;
    }
}

/**
 * 负载测试类
 */
class Hero {
    private String name;

    public Hero(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Hero [name=" + name + "]";
    }

}
