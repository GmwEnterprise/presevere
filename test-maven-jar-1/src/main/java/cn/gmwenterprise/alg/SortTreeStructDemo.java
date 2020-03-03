package cn.gmwenterprise.alg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 理解排序树
 */
@RestController
@SpringBootApplication
public class SortTreeStructDemo {


    public static <E extends Comparable<E>> boolean lessThan(E a, E b) {
        return a.compareTo(b) < 0;
    }

    /**
     * 通过递归从数组构建二叉排序数
     *
     * @param array 数组，元素可比较
     * @param <E>   可比较类型
     * @return 二叉排序树root节点
     */
    public static <E extends Comparable<E>> Node<E> generateTree(E[] array) {
        Node<E> root = null;
        for (E item : array) {
            var node = new Node<>(item);
            if (root == null) {
                root = node;
            } else {
                g(root, node);
            }
        }
        return root;
    }

    private static <E extends Comparable<E>> void g(Node<E> root, Node<E> node) {
        if (lessThan(node.value, root.value)) {
            // 该节点值小于root节点
            if (root.left == null) {
                root.left = node;
            } else {
                g(root.left, node);
            }
        } else {
            // 该节点值大于root节点
            if (root.right == null) {
                root.right = node;
            } else {
                g(root.right, node);
            }
        }
    }

    /**
     * 二叉排序树增加节点
     *
     * @param tree     二叉排序树的根节点
     * @param elements 待插入元素
     * @param <E>      待插入元素类型
     */
    @SafeVarargs
    private static <E extends Comparable<E>> void addNode(Node<E> tree, E... elements) {
        for (E element : elements) {
            var node = new Node<>(element);
            a(tree, node);
        }
    }

    private static <E extends Comparable<E>> void a(Node<E> node, Node<E> newNode) {
        if (lessThan(newNode.value, node.value)) {
            // 当前节点应在左边
            if (node.left == null) {
                node.left = newNode;
            } else {
                a(node.left, newNode);
            }
        } else {
            // 当前节点应在右边
            if (node.right == null) {
                node.right = newNode;
            } else {
                a(node.right, newNode);
            }
        }
    }

    @SafeVarargs
    private static <E extends Comparable<E>> void deleteNode(Node<E> tree, E... elements) {
        for (E element : elements) {
            var result = d(null, tree, element);
            switch (result) {
                case 1:
                    System.out.println("删除元素：" + element);
                    break;
                case 2:
                    System.out.println("根元素无法删除：" + element);
                    break;
                case 3:
                    System.out.println("未找到该元素：" + element);
                default:
            }
        }
    }

    /**
     * @param parent
     * @param node
     * @param element
     * @param <E>
     * @return 1 - 成功删除元素；2 - 根节点，特殊处理；3 - 未找到该元素
     */
    private static <E extends Comparable<E>> int d(Node<E> parent, Node<E> node, E element) {
        if (node.value == element) {
            // 删除该节点并返回true
            if (parent == null) {
                // 根节点
                return 2;
            } else if (node.left == null && node.right == null) {
                // 叶子节点
                if (parent.left == node) {
                    parent.left = null;
                } else {
                    parent.right = null;
                }
            } else {
                // 与左子树的最右节点或右子树的最左节点替换值，然后删除替换后的最值节点
                if (node.left == null) {
                    // 替换右子树最左节点
                    node.value = getLeftNode(node, node.right).value;
                } else {
                    // 替换左子树最右节点
                    node.value = getRightNode(node, node.left).value;
                }
            }
            return 1;
        } else {
            if (node.left != null && d(node, node.left, element) == 1) {
                return 1;
            } else if (node.right != null) {
                return d(node, node.right, element);
            }
            return 3;
        }
    }

    private static <E extends Comparable<E>> Node<E> getLeftNode(Node<E> parent, Node<E> node) {
        if (node.left != null) {
            return getLeftNode(node, node.left);
        } else {
            parent.right = node.right;
            return node;
        }
    }

    private static <E extends Comparable<E>> Node<E> getRightNode(Node<E> parent, Node<E> node) {
        if (node.right != null) {
            return getRightNode(node, node.right);
        } else {
            parent.left = node.left;
            return node;
        }
    }

    /**
     * 二叉排序树线索化
     * 中序遍历
     *
     * @param root 头节点
     * @param <E>  可比较元素
     */
    public static <E extends Comparable<E>> void threadedBinaryTree(Node<E> root) {
        if (root != null) {
            var list = new ArrayList<Node<E>>();
            readNode(list, root);
            for (int i = 0; i < list.size(); i++) {
                var node = list.get(i);
                boolean canThreadedRight = node.right == null && i + 1 < list.size(),
                    canThreadedLeft = node.left == null;
                if (i == 0) {
                    if (canThreadedRight) {
                        node.right = list.get(i + 1);
                        node.rightTag = 1;
                    }
                } else if (i == list.size() - 1) {
                    if (canThreadedLeft) {
                        node.left = list.get(i - 1);
                        node.leftTag = 1;
                    }
                } else {
                    if (canThreadedRight) {
                        node.right = list.get(i + 1);
                        node.rightTag = 1;
                    }
                    if (canThreadedLeft) {
                        node.left = list.get(i - 1);
                        node.leftTag = 1;
                    }
                }
            }
        }
    }

    private static <E extends Comparable<E>> void readNode(List<Node<E>> list, Node<E> node) {
        if (node.left != null) {
            readNode(list, node.left);
        }
        list.add(node);
        if (node.right != null) {
            readNode(list, node.right);
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(SortTreeStructDemo.class, args);
    }

    @GetMapping("/test")
    public Node<Integer> test() {

        var array = new Integer[]{5, 2, 6, 7, 4, 9};
        var tree = generateTree(array);

        addNode(tree, 1, 3, 8);

        deleteNode(tree, 6, 4);

//        threadedBinaryTree(tree);
//        System.out.println();

        return tree;
    }
}
