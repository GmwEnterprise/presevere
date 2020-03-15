package cn.gmwenterprise.algs4;

/**
 * 2-3查找树的基本实现
 */
public class TwoThreeSearchTree<Key extends Comparable<Key>, Value> {

    // 基本结构

    interface Node {
    }

    class TwoNode implements Node {
        Key k;
        Node left, right;
    }

    class ThreeNode implements Node {
        Key k1, k2;
        Node left, middle, right;
    }

    // 总体结构

    Node root = null; // 根节点
    int height = 0; // 树的高度

    /**
     * 插入
     */
    public void insert(Node node) {
    }

    /**
     * 删除
     */
    public void delete(Key key) {
    }

    /**
     * 查找
     */
    public boolean search(Key key) {
        return false;
    }
}
