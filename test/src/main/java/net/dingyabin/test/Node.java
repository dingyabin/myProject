package net.dingyabin.test;

/**
 * Created by MrDing
 * Date: 2019/2/16.
 * Time:3:27
 */
public class Node {

    public int data;
    public Node up;
    public Node down;
    public Node left;
    public Node right;

    public Node(int data) {
        this.data = data;
    }

    public void set(Node up, Node down, Node left, Node right) {
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
    }


    public static Node create() {
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        Node n4 = new Node(4);
        Node n5 = new Node(5);
        Node n6 = new Node(6);
        Node n7 = new Node(7);
        Node n8 = new Node(8);
        Node n9 = new Node(9);

        n1.set(null, n4, null, n2);
        n2.set(null, n5, n1, n3);
        n3.set(null, n6, n2, null);
        n4.set(n1, n7, null, n5);
        n5.set(n2, n8, n4, n6);
        n6.set(n3, n9, n5, null);
        n7.set(n4, null, null, n8);
        n8.set(n5, null, n7, n9);
        n9.set(n6, null, n8, null);
        return n1;
    }

    public static void main(String[] args) {
        Node node = create();
        print(node);

    }


    public static void print(Node node) {
        if (node == null) {
            return;
        }
        System.out.println(node.data);
        print(node.right);
        if (node.left == null) {
            print(node.down);
        }
    }

    public static void print2(Node node) {
        if (node == null) {
            return;
        }
        Node nextDown = node;
        while (nextDown != null) {
            Node nextRight = nextDown;
            while (nextRight != null) {
                System.out.println(nextRight.data);
                nextRight = nextRight.right;
            }
            nextDown = nextDown.down;
        }
    }


}
