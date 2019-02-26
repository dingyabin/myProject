package tree;

import queue.LinkedListQueue;

import java.util.Stack;

/**
 * Created by MrDing
 * Date: 2018/7/3.
 * Time:22:26
 */
public class BST<E extends Comparable> {

    private Node<E> root;

    private int size;

    public BST() {
    }

    public BST(Node<E> root) {
        this.root = root;
    }

    public int getSize() {
        return this.size;
    }

    public boolean isEmpty() {
        return size == 0;
    }


    public void add(E e) {
        if (root == null) {
            root = new Node<>(e);
            size++;
            return;
        }
        Node<E> current = root;
        while (true) {
            if (e.compareTo(current.e) == 0) {
                break;
            }
            if (e.compareTo(current.e) < 0 && current.left == null) {
                current.left = new Node<>(e);
                size++;
                break;
            }
            if (e.compareTo(current.e) > 0 && current.right == null) {
                current.right = new Node<>(e);
                size++;
                break;
            }
            if (e.compareTo(current.e) < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
    }


    public void add2(E e) {
        if (root == null) {
            root = new Node<>(e);
            size++;
            return;
        }
        _add(root, e);
    }


    private void _add(Node<E> node, E e) {
        if (e.compareTo(node.e) == 0) {
            return;
        }
        if (e.compareTo(node.e) < 0 && node.left == null) {
            node.left = new Node<>(e);
            size++;
            return;
        }
        if (e.compareTo(node.e) > 0 && node.right == null) {
            node.right = new Node<>(e);
            size++;
            return;
        }

        if (e.compareTo(node.e) < 0) {
            _add(node.left, e);
        } else {
            _add(node.right, e);
        }
    }


    //返回插入新节点之后的根
    private Node<E> _add2(Node<E> node, E e) {
        if (node == null) {
            size++;
            return new Node<>(e);
        }
        if (e.compareTo(node.e) < 0) {
            node.left = _add2(node.left, e);
        }

        if (e.compareTo(node.e) > 0) {
            node.right = _add2(node.right, e);
        }
        return node;
    }


    public boolean contains(E e) {
        return _contains(root, e);
    }


    public boolean _contains(Node<E> node, E e) {
        if (node == null) {
            return false;
        }
        if (node.e.equals(e)) {
            return true;
        }
        if (e.compareTo(node.e) < 0) {
            return _contains(node.left, e);
        } else {
            return _contains(node.right, e);
        }
    }


    public void preOrder() {
        _preOrder(root);
    }

    private void _preOrder(Node<E> node) {
        if (node == null) {
            return;
        }
        System.out.println(node.e);
        _preOrder(node.left);
        _preOrder(node.right);
    }


    public void preOrderNR() {
        Stack<Node> stack=new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()){
            Node pop = stack.pop();
            System.out.println(pop.e);
            if (pop.right!=null){
                stack.push(pop.right);
            }
            if (pop.left!=null){
                stack.push(pop.left);
            }
        }
    }



    public void inOrder() {
        _inOrder(root);
    }

    private void _inOrder(Node<E> node) {
        if (node == null) {
            return;
        }
        _inOrder(node.left);
        System.out.println(node.e);
        _inOrder(node.right);
    }


    public void postOrder() {
        _postOrder(root);
    }

    private void _postOrder(Node<E> node) {
        if (node == null) {
            return;
        }
        _postOrder(node.left);
        _postOrder(node.right);
        System.out.println(node.e);
    }



    public void levelOrder(){
        LinkedListQueue<Node>  queue=new LinkedListQueue<>();
        queue.enqueue(root);
        while (!queue.isEmpty()){
            Node node = queue.dequeue();
            System.out.println(node.e);
            if (node.left!=null){
                queue.enqueue(node.left);
            }
            if (node.right!=null){
                queue.enqueue(node.right);
            }
        }
    }





    public void removeMax(){
    }





    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        printStr(root,sb,0);
        return sb.toString();
    }

    private void printStr(Node<E> node, StringBuilder sb,int depth) {
        if (node == null) {
            sb.append(printDepth(depth)).append("null\n");
            return;
        }
        sb.append(printDepth(depth)).append(node.e).append("\n");
        printStr(node.left,sb,depth+1);
        printStr(node.right,sb,depth+1);
    }

    private String printDepth(int depth) {
        String placeholder = "";
        for (int i = 0; i < depth; i++) {
            placeholder += "--";
        }
        return placeholder;
    }


    private class Node<T> {
        public T e;
        public Node<T> left;
        public Node<T> right;

        public Node(T e) {
            this.e = e;
            this.left = null;
            this.right = null;
        }
    }

}
