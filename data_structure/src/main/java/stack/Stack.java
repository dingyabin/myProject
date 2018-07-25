package stack;

/**
 * Created by MrDing
 * Date: 2018/6/30.
 * Time:20:01
 */
public interface Stack<E> {

    void push(E e);

    E pop();

    E peek();

    int getSize();

    boolean isEmpty();

}
