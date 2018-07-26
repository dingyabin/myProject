package net.dingyabin.thread;

import java.util.Random;
import java.util.concurrent.SynchronousQueue;

/**
 * Created by MrDing
 * Date: 2018/7/19.
 * Time:23:32
 */
public class SynQueue {

    public static void main(String[] args) throws Exception {
        SynchronousQueue<Integer> sc = new SynchronousQueue<>();
        new Thread(() -> {
            while (true) {
                try {
                    int s= new Random().nextInt(50);
                    System.out.println("添加操作运行完毕..."+s);
                    sc.put(s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            while (true) {
                try {
                    System.out.println("-----------------> sc.take: " + sc.take());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
