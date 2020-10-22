package net.dingyabin.crawl.context;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by MrDing
 * Date: 2018/12/2.
 * Time:1:16
 */
public class Holder {

    public volatile static String lastId = "";


    public static ReentrantLock reentrantLock = new ReentrantLock();



    public static void lock(){
        reentrantLock.lock();
    }



    public static void unlock(){
        reentrantLock.unlock();
    }

    public static boolean islock(){
       return reentrantLock.isLocked();
    }

}
