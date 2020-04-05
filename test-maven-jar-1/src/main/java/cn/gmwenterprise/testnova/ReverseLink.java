package cn.gmwenterprise.testnova;

public class ReverseLink {

    public static void main(String[] args) {
        Node head = new Node(1);
        head.add(2);
        head.add(3);
        head.add(4);
        head.add(5);
        head.add(6);
        head = reverse(head);
        System.out.println(head.show());
    }


    static class Node {
        int value;

        Node(int value) { this.value = value; }

        Node next;

        void add(int num) {
            if (next == null) {
                next = new Node(num);
            } else {
                next.add(num);
            }
        }

        String show() {
            return value + " -> " + (next != null ? next.show() : "");
        }
    }

    private static Node reverse(Node head) {
        Node prev = null, current = head;
        while (current != null) {
            Node temp = current.next;
            current.next = prev;
            prev = current;
            current = temp;
        }
        return prev;
    }
}
