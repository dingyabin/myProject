package array;

import java.util.Arrays;

/**
 * Created by MrDing
 * Date: 2018/6/28.
 * Time:23:47
 */
public class GenericArray<T> {

    private T[] data;
    private int size;

    public GenericArray() {
        this(10);
    }

    @SuppressWarnings("unchecked")
    public GenericArray(int capacity) {
        this.data = (T[]) new Object[capacity];
        this.size = 0;
    }


    public int size() {
        return this.size;
    }


    public int capacity() {
        return data.length;
    }


    public boolean isEmpty() {
        return this.size() == 0;
    }

    public boolean isFull() {
        return this.size() == data.length;
    }


    public void addFirst(T e) {
        add(0, e);
    }

    public void addLast(T e) {
        add(size, e);
    }


    public void add(int index, T e) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("index should >=0 and <= " + size());
        }
        if (isFull()) {
            resize(data.length * 2);
        }

        for (int i = size - 1; i >= index; i--) {
            data[i + 1] = data[i];
        }
        data[index] = e;
        size++;
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("index should >=0 and <" + size());
        }
        return data[index];
    }

    public void set(int index, T e) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("index should >=0 and <" + size());
        }
        data[index] = e;
    }


    public boolean cointains(int e) {
        for (int i = 0; i < size; i++) {
            if (data[i].equals(e)) {
                return true;
            }
        }
        return false;
    }

    public int find(T e) {
        for (int i = 0; i < size; i++) {
            if (data[i].equals(e)) {
                return i;
            }
        }
        return -1;
    }

    public T delete(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("index should >=0 and <" + size());
        }
        T del = data[index];
        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
        }
        size--;
        data[size] = null;
        return del;
    }

    public T deleteFirst() {
        return delete(0);
    }

    public T deleteLast() {
        return delete(size - 1);
    }


    public boolean deleteElement(T e) {
        int index = find(e);
        if (index != -1) {
            delete(index);
        }
        return index != -1;
    }

    //递归，区别于deleteElement
    public boolean deleteAllElement(T e) {
        int index = find(e);
        boolean flag = false;
        while (index != -1) {
            delete(index);
            index = find(e);
            flag = true;
        }
        return flag;
    }

    public boolean deleteAllElement2(T e) {
        int index = find(e);
        if (index == -1) {
            return false;
        } else {
            delete(index);
            return deleteAllElement2(e);
        }
    }


    @Override
    public String toString() {
        return "Array{" +
                "data=" + Arrays.toString(data) +
                ", size=" + size +
                '}';
    }


    @SuppressWarnings("unchecked")
    private void resize(int newCapcaity){
        T[] newdata =  (T[]) new Object[newCapcaity];
        System.arraycopy(data, 0, newdata, 0, data.length);
        data = newdata;
    }
}
