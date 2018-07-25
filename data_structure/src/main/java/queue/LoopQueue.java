package queue;

/**
 * Created by MrDing
 * Date: 2018/6/30.
 * Time:23:18
 */
public class LoopQueue<E> implements Queue<E> {

    private E[] array;
    private int front, tail;
    private int size;

    @SuppressWarnings("unchecked")
    public LoopQueue(int capacity) {
        array = (E[]) new Object[capacity + 1];
        front = 0;
        tail = 0;
        size = 0;
    }

    public LoopQueue() {
        this(10);
    }

    @Override
    public void enqueue(E e) {
        //判断是否满了
        if ((tail + 1) % array.length == front) {
            resize(getCapacity() * 2);
        }
        array[tail] = e;
        tail = (tail + 1) % array.length;
        size++;
    }


    @Override
    public E dequeue() {
        if (isEmpty()) {
            throw new IllegalArgumentException("empty");
        }
        E e = array[front];
        array[front] = null;
        front = (front + 1) % array.length;
        size--;

        if (size == getCapacity() / 4 && getCapacity() / 2 != 0) {
            resize(getCapacity() / 2);
        }
        return e;
    }

    @Override
    public E getFront() {
        if (isEmpty()) {
            throw new IllegalArgumentException("empty");
        }
        return array[front];
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return front == tail;
    }

    @Override
    public int getCapacity() {
        return array.length - 1;
    }


    @SuppressWarnings("unchecked")
    private void resize(int newCapacity) {
        E[] newArray = (E[]) new Object[newCapacity + 1];
        for (int i = 0; i < size; i++) {
            newArray[i] = array[(front + i) % array.length];
        }
        front = 0;
        array = newArray;
        tail = size;
    }
}
