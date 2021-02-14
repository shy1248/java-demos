/**
 * @Date        : 2021-02-12 22:59:18
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : -
 */
package me.shy.demo.composite;

public class BranchNode extends Node {

    public BranchNode(String name) {
        super(name);
    }

    @Override
    public Node addChild(Node node) {
        this.children.add(node);
        return this;
    }

    @Override
    public Node deleteChild(Node node) {
        this.children.remove(node);
        return this;
    }

    @Override
    void show() {
        System.out.println(this.getName());
    }

}
