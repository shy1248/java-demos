/**
 * @Date        : 2021-02-12 22:54:26
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : A base class for node of tree.
 */
package me.shy.demo.composite;

import java.util.ArrayList;
import java.util.List;

public abstract class Node {
    protected List<Node> children = new ArrayList<>();
    protected String name;

    abstract Node addChild(Node node);

    abstract Node deleteChild(Node node);

    abstract void show();

    public boolean hasChildern() {
        return this.children.size() == 0 ? false : true;
    }

    public Node(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
