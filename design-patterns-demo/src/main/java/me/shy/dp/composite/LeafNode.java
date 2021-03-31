/**
 * @Date        : 2021-02-12 22:56:17
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : A base class for leaf node, which has no child node
 */
package me.shy.dp.composite;

public class LeafNode extends Node {



    public LeafNode(String name) {
        super(name);
    }

    @Override
    void show() {
        System.err.println(this.getName());
    }

    @Override
    Node addChild(Node node) {
        throw new RuntimeException("LeafNode can not be added a child node.");
    }

    @Override
    Node deleteChild(Node node) {
        throw new RuntimeException("LeafNode has not any childern nodes.");
    }

}
