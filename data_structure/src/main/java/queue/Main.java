package queue;

import java.util.Random;

/**
 * Created by MrDing
 * Date: 2018/7/1.
 * Time:0:55
 */
public class Main {

    public static void main(String[] args) {
        int count = 100000;
        count(new ArrayQueeu<>(), count);
        count(new LoopQueue<>(), count);
        count(new LinkedListQueue<>(), count);

    }

    public static void count(Queue<Integer> queue, int count) {
        Random random = new Random();
        long start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            queue.enqueue(random.nextInt());
        }
        for (int i = 0; i < count; i++) {
            queue.dequeue();
        }
        long end = System.currentTimeMillis();
        System.out.println(queue.getClass().getName() + ":" + (end - start) / 1000.0 + "s");
    }
}
