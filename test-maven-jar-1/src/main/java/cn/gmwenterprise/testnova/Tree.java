package cn.gmwenterprise.testnova;

import java.util.List;

public class Tree {

    static abstract class Node {
        List<Node> children;

        abstract boolean addChild(Node node);
    }

    Node root;

    public boolean put(Node node) {
        if (root == null) {
            root = node;
            return true;
        } else {
            boolean ans = false;
            for (Node item : root.children) {
                if (item.addChild(node)) {
                    ans = true;
                    break;
                }
            }
            return ans;
        }
    }
}
