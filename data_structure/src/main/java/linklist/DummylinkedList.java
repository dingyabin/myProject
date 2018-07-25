package linklist;

/**
 * Created by MrDing
 * Date: 2018/7/1.
 * Time:15:47
 */
public class DummylinkedList<E> {

    private Node dummyHead;
    private int size;


    public DummylinkedList() {
        dummyHead = new Node(null, null);
        size = 0;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void addFirst(E e) {
        add(0, e);
    }

    public void addLast(E e) {
        add(size, e);
    }

    public void add(int index, E e) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("index 不合法...");
        }

        Node pre = dummyHead;
        for (int i = 0; i < index; i++) {
            pre = dummyHead.next;
        }
        pre.next = new Node(e, pre.next);
        size++;

    }


    public E getFirst() {
        return get(0);
    }

    public E getLast() {
        return get(size - 1);
    }


    public E get(int index) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("index 不合法...");
        }
        Node current = dummyHead.next;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.e;
    }


    public void set(int index, E e) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("index 不合法...");
        }
        Node current = dummyHead.next;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        current.e = e;
    }


    public boolean contains(E e) {
        Node current = dummyHead.next;
        while (current != null) {
            if (current.e.equals(e)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }


    public E delete(int index) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("index 不合法...");
        }
        Node pre = dummyHead;
        for (int i = 0; i < index; i++) {
            pre = pre.next;
        }
        Node del = pre.next;
        pre.next = del.next;
        del.next = null;
        size--;
        return del.e;
    }

    public E deleteFirst(){
       return  delete(0);
    }


    public E deleteLast(){
        return delete(size - 1);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node current = dummyHead.next;
        while (current != null) {
            sb.append(current.e);
            sb.append("->");
            current = current.next;
            System.out.println(sb.toString());
        }
        sb.append("NULL");
        return sb.toString();
    }

    private class Node {
        public E e;
        public Node next;

        public Node(E e, Node next) {
            this.e = e;
            this.next = next;
        }

        public Node(E e) {
            this.e = e;
        }

        public Node() {
        }
    }

}
