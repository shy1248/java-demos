/**
 * @Date        : 2021-02-15 21:25:07
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 使用单向链表模拟水浒英雄的排名
 *
 * 1. 实现带头节点的单向链表存储；
 * 2. 实现按添加英雄的顺序存储；
 * 3. 实现按英雄编号顺序存储，如果英雄的编号已存在于链表中，就提示对应编号的英雄已存在，并且不再插入；
 */
package me.shy.demo.list;

public class UnidirectionalLinkedListDemo {
    public static void main(String[] args) {
        // 创建一些英雄节点
        // 注意：链表的顺序是保存在 HeroNode 里边
        HeroNode h1 = new HeroNode(1, "宋江", "及时雨");
        HeroNode h2 = new HeroNode(4, "林冲", "豹子头");
        HeroNode h3 = new HeroNode(3, "吴用", "智多星");
        HeroNode h4 = new HeroNode(2, "卢俊义", "玉麒麟");

        // 创建链表
        UnidirectinalLinkedList list = new UnidirectinalLinkedList();
        // 加入节点
        list.add(h1);
        list.add(h2);
        list.add(h3);
        list.add(h4);
        // 输出链表
        System.out.println("尾部追加：");
        list.list();

        UnidirectinalLinkedList orderedList = new UnidirectinalLinkedList();
        orderedList.insertByHeroNo(h1);
        orderedList.insertByHeroNo(h2);
        orderedList.insertByHeroNo(h3);
        orderedList.insertByHeroNo(h4);
        orderedList.insertByHeroNo(h3);
        System.out.println("按英雄编号插入：");
        orderedList.list();

        UnidirectinalLinkedList updateList = new UnidirectinalLinkedList();
        System.out.println(list == updateList);
        updateList.insertByHeroNo(h1);
        updateList.insertByHeroNo(h2);
        updateList.insertByHeroNo(h3);
        updateList.insertByHeroNo(h4);
        updateList.insertByHeroNo(h3);
        System.out.println("修改之前的链表：");
        updateList.list();
        HeroNode h5 = new HeroNode(2, "^卢俊义^", "*玉麒麟*");
        HeroNode h6 = new HeroNode(5, "鲁智深", "花和尚");
        updateList.update(h5);
        updateList.update(h6);
        System.out.println("修改之后的链表：");
        updateList.list();

        UnidirectinalLinkedList deleteList = new UnidirectinalLinkedList();
        System.out.println(list == updateList);
        deleteList.insertByHeroNo(h1);
        deleteList.insertByHeroNo(h2);
        deleteList.insertByHeroNo(h3);
        deleteList.insertByHeroNo(h4);
        deleteList.insertByHeroNo(h3);
        System.out.println("删除之前的链表：");
        deleteList.list();
        HeroNode h8 = new HeroNode(5, "鲁智深", "花和尚");
        deleteList.delete(h1);
        deleteList.delete(h8);
        deleteList.delete(h2);
        deleteList.delete(h3);
        deleteList.delete(h4);
        System.out.println("删除之后的链表：");
        deleteList.list();
    }
}

class UnidirectinalLinkedList {
    // 单向链表的头节点，不存放任何数据
    private HeroNode head = null;

    /**
     * 构造方法，初始化空的头节点
     */
    public UnidirectinalLinkedList() {
        this.head = new HeroNode(0, null, null);
    }

    /**
     * 在链表的尾部增加新的节点
     * 1.遍历链表，找到当前链表的尾部节点；
     * 2.将尾部节点的 next 指向待加入的节点即可；
     * @param node
     */
    public void add(HeroNode node) {
        // 因为单向链表的头节点不能动，因此需要引入临时变量进行遍历
        // 该临时节点 current 在遍历时只要 current.next 不为空就需要将 current.next 迭代给 current
        HeroNode current = this.head;
        while (true) {
            // 已到链表的尾部，停止遍历
            if (null == current.getNext()) {
                break;
            }
            // 否则继续向下一个节点迭代
            current = current.getNext();
        }
        // 找到链表的尾部后，将当前的尾部节点的下一个节点指向待插入的新节点
        current.setNext(node);
    }

    /**
     * 根据英雄的编号顺序插入英雄到链表对应的位置
     * 1.首先需要通过遍历的方式找到需要插入节点的前一个节点，因为是单向的链表，不能等到找到要插入位置的下一个节点再做插入；
     * 2.找到对应位置后需要判断当前节点的下一个节点的英雄编号与待插入的英雄编号是否相等，若相等，使用标识位记录；
     * 3.将待插入节点的下一个节点指向当前节点的的下一个节点；
     * 4.将当前节点的下一个节点指向待插入的节点，完成插入；
     * @param node
     */
    public void insertByHeroNo(HeroNode node) {
        HeroNode current = this.head;
        // 标志为，代表待插入节点的英雄编号是否已经存在，默认为不存在
        boolean isExists = false;
        // 开始遍历，查找待插入的位置的前一个节点
        while (true) {
            // 已遍历到链表尾部，说明待插入的英雄节点的编号大于链表中已存在的所有英雄编号
            // 待插入的英雄节点就应该插入到链表的尾部
            if (null == current.getNext()) {
                break;
            }
            // 当前英雄节点的下一个节点的编号大于等于待插入的英雄节点的编号，说明待插入的节点应该在当前节点之后
            // 不能判断当前节点的编号与待插入节点的编号的大小关系，若当前节点的编号大于了待插入的节点编号
            // 则说明待插入的节点应该放在当前节点的前面，由于是单向链表，已经无法找到上一个节点了
            if (current.getNext().getNo() >= node.getNo()) {
                if (current.getNext().getNo() == node.getNo()) {
                    // 当前英雄节点的下一个节点编号与待插入的英雄节点的编号相等
                    // 表示待插入的英雄节点编号已存在，将标志位设为 true
                    isExists = true;
                }
                break;
            }
            // 对节点进行迭代
            current = current.getNext();
        }

        // 找到待插入节点的插入位置后，若待插入编号的节点已经存在，则提示并不再插入
        // 否则进行插入
        if (isExists) {
            System.err.printf("待插入的英雄编号 %d 已经存在！\n", node.getNo());
        } else {
            // 将待插入节点的下一个节点指向当前节点的下一个节点
            node.setNext(current.getNext());
            // 将当前节点的下一个节点指向待插入的节点
            current.setNext(node);
        }
    }

    /**
     * 根据给定节点的编号，来更新已存在的对于编号节点的信息
     * 如果链表为空或者给定节点的编号不存在于链表中，则给出对应的提示
     * 注意，不能修改节点编号
     * @param node
     */
    public void update(HeroNode node) {
        // 标志为，代表是否在链表中是否找到给定节点对应编号的节点
        boolean hasExists = false;
        // 链表为空
        if (null == this.head.getNext()) {
            System.err.println("Empty linked list.");
            return;
        }
        // 遍历链表，查找目标节点
        HeroNode current = this.head;
        while (true) {
            // 如果找到给定的对应编号的节点，停止迭代并标记
            if (current.getNo() == node.getNo()) {
                hasExists = true;
                break;
            }
            // 如果到链表尾部，停止迭代
            if (null == current.getNext()) {
                break;
            }
            // 节点迭代
            current = current.getNext();
        }

        if (hasExists) {
            // 如果找到对应编号的信息，则修改对应信息
            current.setName(node.getName());
            current.setNickName(node.getNickName());
        } else {
            // 否则提示未找到对应节点
            System.err.printf("未找到对应编号 %d 的英雄节点。\n", node.getNo());
        }
    }

    /**
     * 根据给定节点的编号，来删除已存在的对于编号节点的信息
     * 如果链表为空或者给定节点的编号不存在于链表中，则给出对应的提示
     * @param node
     */
    public void delete(HeroNode node) {
        // 标志为，代表是否在链表中是否找到给定节点对应编号的节点
        boolean hasExists = false;
        // 链表为空
        if (null == this.head.getNext()) {
            System.err.println("Empty linked list.");
            return;
        }
        // 遍历链表，查找目标节点，需要找到待删除节点的前一个节点
        HeroNode current = this.head;
        while (true) {
            // 如果到链表尾部，停止迭代
            if (null == current.getNext()) {
                break;
            }
            // 如果找到给定的对应编号的节点，停止迭代并标记
            if (current.getNext().getNo() == node.getNo()) {
                hasExists = true;
                break;
            }
            // 节点迭代
            current = current.getNext();
        }

        // 如果找到对应编号的前一个节点，则修改其下一个节点指向当前节点的下一个节点的下一个节点
        if (hasExists) {
            current.setNext(current.getNext().getNext());
        } else {
            // 否则提示未找到对应节点
            System.err.printf("未找到对应编号 %d 的英雄节点。\n", node.getNo());
        }
    }

    /**
     * 打印链表的内容，遍历链表并输出
     */
    public void list() {
        // 空的链表，不输出
        if (null == this.head.getNext()) {
            System.out.println("Empty linked list.");
            return;
        }
        // 开始遍历，临时节点首先指向头节点的下一个节点
        HeroNode current = this.head.getNext();
        while (true) {
            // 输出节点
            System.out.println(current);
            // 如果头节点的下一个节点为空或者已遍历到链表尾部，终止循环
            if (null == current || null == current.getNext()) {
                break;
            }
            // 迭代下一个节点
            current = current.getNext();
        }
    }
}

/**
 * 英雄节点
 */
class HeroNode {
    // 英雄编号
    private int no;
    // 英雄名称
    private String name;
    // 英雄花名
    private String nickName;
    // 下一个节点
    private HeroNode next;

    public HeroNode(int no, String name, String nickName) {
        this.no = no;
        this.name = name;
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "HeroNode [no=" + no + ", name=" + name + ", nickName=" + nickName + "]";
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public HeroNode getNext() {
        return next;
    }

    public void setNext(HeroNode next) {
        this.next = next;
    }
}
