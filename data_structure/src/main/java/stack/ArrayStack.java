package stack;

import array.GenericArray;

/**
 * Created by MrDing
 * Date: 2018/6/30.
 * Time:20:03
 */
public class ArrayStack<E> implements Stack<E> {


    private GenericArray<E> array;

    public ArrayStack() {
        array = new GenericArray<>();
    }

    public ArrayStack(int capacity) {
        array = new GenericArray<>(capacity);
    }

    @Override
    public void push(E e) {
        array.addLast(e);
    }

    @Override
    public E pop() {
        return array.deleteLast();
    }

    @Override
    public E peek() {
        return array.getLast();
    }

    @Override
    public int getSize() {
        return array.size();
    }

    @Override
    public boolean isEmpty() {
        return array.isEmpty();
    }

    @Override
    public String toString() {
        return "ArrayStack{" +
                "array=" + array +
                " top}";
    }
}
