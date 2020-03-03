package cn.gmwenterprise.alg;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Node<E extends Comparable<E>> {
    E value = null;
    Node<E> left = null, right = null; // 左右子树指针

    int leftTag = 0, rightTag = 0; // 为0则表示对应的left、right指针为孩子节点；否则为线索节点

    public Node(E item) {
        value = item;
    }

    public Node() {}

    public E getValue() {
        return value;
    }

    public void setValue(E value) {
        this.value = value;
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