package cn.gmwenterprise.algs4;

/**
 * 红黑树
 */
public class RedBlackBST<Key extends Comparable<Key>, Value> {

    // 结构定义

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private class Node {
        Key key;
        Value value;
        Node left, right;
        boolean color;

        Node(Key k, Value v, boolean c) {
            key = k;
            value = v;
            color = c;
        }
    }

    // 是否为红节点，即指向它的链接为红链接
    private boolean isRed(Node x) {
        if (x == null) return false;
        return x.color == RED;
    }

    // 左旋，并返回旋转后的新的节点信息
    Node rotateLeft(Node h) {
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        return x;
    }

    // 右旋，并返回旋转后的新的节点信息
    Node rotateRight(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        return x;
    }

    // 将两条红链接涂黑，然后将红链接向上传递
    void flipColors(Node h) {
        h.color = RED;
        h.left.color = BLACK;
        h.right.color = BLACK;
    }

    // 成员定义

    private Node root;

    public void put(Key key, Value val) {
        root = put(root, key, val);
        root.color = BLACK;
    }

    private Node put(Node h, Key key, Value val) {
        if (h == null) {
            return new Node(key, val, RED);
        }
        int cmp = key.compareTo(h.key);
        if (cmp < 0) {
            // 插入到h的左子树
            h.left = put(h.left, key, val);
        } else if (cmp > 0) {
            // 插入到h的右子树
            h.right = put(h.right, key, val);
        } else {
            // 如果键值相同则替换
            h.value = val;
        }
        // 旋转、调色
        if (isRed(h.right) && !isRed(h.left)) {
            h = rotateLeft(h);
        }
        if (isRed(h.left) && isRed(h.left.left)) {
            h = rotateRight(h);
        }
        if (isRed(h.left) && isRed(h.right)) {
            flipColors(h);
        }
        return h;
    }

    // test
    int count = 0;

    public Value get(Key key) {
        Value result = search(root, key);
        System.out.printf("查找key[%s]迭代次数：%d%n", key, count);
        count = 0;
        return result;
    }

    private Value search(Node h, Key key) {
        count++;
        if (h == null) {
            return null;
        }
        var cmp = h.key.compareTo(key);
        if (cmp == 0) {
            return h.value;
        } else if (cmp < 0) {
            return search(h.right, key);
        } else {
            return search(h.left, key);
        }
    }

    public static void main(String[] args) {

        var tree = new RedBlackBST<Long, String>();
        for (long i = 0; i < 10000000L; i++) {
            tree.put(i, "the value [" + i + "]");
        }
        // 一千万的数据中只需要迭代23次便能查找到相对应的值
        System.out.println(tree.get(9999999L));
    }
}
