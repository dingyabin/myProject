package queue;

import array.GenericArray;

/**
 * Created by MrDing
 * Date: 2018/6/30.
 * Time:22:48
 */
public class ArrayQueeu<E> implements Queue<E> {

    private GenericArray<E> array;

    public ArrayQueeu() {
        this.array=new GenericArray<>();
    }

    public ArrayQueeu(int capacity) {
        this.array=new GenericArray<>(capacity);
    }

    @Override
    public void enqueue(E e) {
        array.addLast(e);
    }

    @Override
    public E dequeue() {
       return array.deleteFirst();
    }

    @Override
    public E getFront() {
        return array.getFirst();
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
    public int getCapacity() {
        return array.capacity();
    }
}
