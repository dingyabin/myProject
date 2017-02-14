package net.dingyabin.com;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by MrDing
 * Date: 2017/2/14.
 * Time:21:31
 */
public class MyRunable implements Runnable {

    private int index;

    private AtomicInteger atomicInteger;

    public MyRunable(int index, AtomicInteger atomicInteger) {
        this.index = index;
        this.atomicInteger = atomicInteger;
    }

    @Override
    public void run() {
        atomicInteger.getAndIncrement();
        System.out.println(Thread.currentThread().getName()+"开始执行任务"+index);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+"执行任务完毕"+index);
    }
}
