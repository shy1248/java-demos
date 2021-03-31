/**
 * @Date        : 2021-02-12 22:54:07
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : Moke print a directory and it's subdirectories and files like a tree.
 */
package me.shy.dp.composite;

public class TreeOut {

    public static void main(String[] args) {
        Node root = new BranchNode("demo-app");
        Node bin = new BranchNode("bin");
        Node etc = new BranchNode("etc");
        Node lib = new BranchNode("lib");
        Node java = new LeafNode("java");
        Node javac = new LeafNode("javac");
        Node conf = new BranchNode("global.conf");
        Node confd = new BranchNode("confs.d");
        Node demo = new LeafNode("demo.conf");
        Node rt = new LeafNode("rt.jar");
        Node readme = new LeafNode("README");
        Node notice = new LeafNode("NOTICE");

        root.addChild(bin);
        root.addChild(etc);
        root.addChild(lib);
        root.addChild(readme);
        root.addChild(notice);
        bin.addChild(java);
        bin.addChild(javac);
        etc.addChild(conf);
        etc.addChild(confd);
        confd.addChild(demo);
        lib.addChild(rt);

        try {
            // try add a node to a LeafNode, this must be occourded an error
            // notice.addChild(readme);
            // try delete a node from a LeafNode, this must be occourded an error
            notice.deleteChild(readme);
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
        }

        TreeOut.tree(root, 0);
    }

    static void tree(Node node, int level) {
        System.out.println(String.format("%s%s", getBlankPrefix(level), node.getName()));
        level++;
        if (! node.hasChildern()) {
            return;
        }
        int childrenSize = node.children.size();
        for (int i = 0; i < childrenSize; i++) {
            Node child = node.children.get(i);
            if (i == childrenSize - 1) {
                child.setName(String.format("└── %s", child.getName()));
            } else {
                child.setName(String.format("├── %s", child.getName()));
            }
            tree(child, level);
        }
    }

    static String getBlankPrefix(int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            if (i == 0) {
                sb.append("    ");
            } else {
                sb.append("|   ");
            }
        }
        return sb.toString();
    }
}
