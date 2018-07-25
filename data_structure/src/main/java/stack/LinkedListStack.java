package stack;

import linklist.DummylinkedList;
import stack.Stack;

/**
 * Created by MrDing
 * Date: 2018/7/1.
 * Time:21:17
 */
public class LinkedListStack<E> implements Stack<E> {

    private DummylinkedList<E> list;

    public LinkedListStack() {
        list = new DummylinkedList<>();
    }

    @Override
    public void push(E e) {
        list.addFirst(e);
    }

    @Override
    public E pop() {
        return list.deleteFirst();
    }

    @Override
    public E peek() {
        return list.getFirst();
    }

    @Override
    public int getSize() {
        return  list.getSize();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public String toString() {
        return "LinkedListStack{" +
                "list=" + list +
                '}';
    }
}
