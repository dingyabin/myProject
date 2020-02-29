package net.dingyabin.thread;

/**
 * Created by MrDing
 * Date: 2018/7/20.
 * Time:23:13
 */
public class WaitNotify {

    private volatile int index = 0;

    private class PrintO implements Runnable {
        @Override
        public void run() {
            synchronized (WaitNotify.class) {
                while (true) {
                    System.out.println(Thread.currentThread().getName() + "----------" + index++);
                    _sleep();
                    WaitNotify.class.notify();
                    _wait();
                }
            }
        }
    }


    private class PrintJ implements Runnable {
        @Override
        public void run() {
            synchronized (WaitNotify.class) {
                while (true) {
                    System.out.println(Thread.currentThread().getName() + "----------" + index++);
                    _sleep();
                    WaitNotify.class.notify();
                    _wait();
                }
            }
        }
    }


    private void _wait() {
        try {
            WaitNotify.class.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void _sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        WaitNotify printO = new WaitNotify();
        new Thread(printO.new PrintO(), "PrintO").start();
        new Thread(printO.new PrintJ(), "PrintJ").start();
    }

}
