package net.dingyabin.thread;

/**
 * Created by MrDing
 * Date: 2018/7/20.
 * Time:23:13
 */
public class WaitNotify {

    private volatile int index = 0;

    private final Object obj = new Object();

    private class PrintO implements Runnable {
        @Override
        public void run() {
            synchronized (obj) {
                while (true) {
                    if (index % 2 != 0) {
                        try {
                            obj.wait();
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println(Thread.currentThread().getName() + "----------" + index++);
                    obj.notifyAll();
                }
            }
        }
    }

    private class PrintJ implements Runnable {
        @Override
        public void run() {
            synchronized (obj) {
                while (true) {
                    if (index % 2 == 0) {
                        try {
                            obj.wait();
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println(Thread.currentThread().getName() + "----------" + index++);
                    obj.notifyAll();
                }
            }
        }
    }


    public static void main(String[] args) {
        WaitNotify printO = new WaitNotify();
        new Thread(printO.new PrintO(), "PrintO").start();
        new Thread(printO.new PrintJ(), "PrintJ").start();
    }

}
