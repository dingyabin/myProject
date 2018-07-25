package queue;

/**
 * Created by MrDing
 * Date: 2018/6/30.
 * Time:22:47
 */
public interface Queue<E> {

    void enqueue(E e);

    E dequeue();

    E getFront();

    int getSize();

    boolean isEmpty();

    int getCapacity();
}
