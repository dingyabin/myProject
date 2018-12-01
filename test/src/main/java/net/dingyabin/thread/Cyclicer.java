package net.dingyabin.thread;

import java.util.concurrent.CyclicBarrier;

/**
 * Created by MrDing
 * Date: 2018/11/8.
 * Time:21:58
 */
public class Cyclicer {

    private final CyclicBarrier orderLock = new CyclicBarrier(2);
    private final CyclicBarrier allDoneLock   = new CyclicBarrier(2);


    private volatile int start = 1;

    public static void main(String[] args) {
        Cyclicer cyclicer=new Cyclicer();
        new Thread(cyclicer.new TaskA()).start();
        new Thread(cyclicer.new TaskB()).start();
    }



    private class TaskA implements Runnable {
        @Override
        public void run() {
            while (true){
                try {
                    System.out.println("A_" + start++);
                    Thread.sleep(1000);
                    orderLock.await();
                    allDoneLock.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private class TaskB implements Runnable {
        @Override
        public void run() {
            while (true){
                try {
                    orderLock.await();
                    System.out.println("B_" + start++);
                    Thread.sleep(1000);
                    allDoneLock.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }




}
