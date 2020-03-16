package cn.gmwenterprise.alg;

import java.util.LinkedList;

/**
 * 自己实现B树结构
 *
 * @param <K>
 * @param <V>
 */
public class MyBTree<K extends Comparable<K>, V> {

    private Node root; // 根节点
    private int t;

    /**
     * @param m 阶数。对于一颗m阶的B树，设t为{@code Math.ceil(m)}，
     *          则每个节点至少含有t个关键字（根节点除外），至多可以拥
     *          有{@code 2t-1}个关键字。如果计算出的t小于2，则设置
     *          最小值为2，即呈现出来的b树结构为2-3-4树
     */
    public MyBTree(int m) {
        t = (int) Math.ceil(m);
        t = t > 1 ? t : 2;
    }

    private class Node {
        LinkedList<K> keys; // 存放该节点所包含关键字，所有关键字按升序存放
        LinkedList<V> values; // 对应keys的下标存放值
        int keyLen; // 该节点含有关键字数量。最大为2t-1，最小为t（根节点最小为1）
        LinkedList<Node> children; // 存放子节点指针。该节点可拥有keysLength+1个子节点指针
        boolean isLeaf; // 该节点是否为叶子节点
    }
}
