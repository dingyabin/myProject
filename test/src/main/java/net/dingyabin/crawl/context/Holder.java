package net.dingyabin.crawl.context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by MrDing
 * Date: 2018/12/2.
 * Time:1:16
 */
public class Holder {


    public volatile static String lastId = "";


    public static ReentrantLock reentrantLock = new ReentrantLock();


    public static List<String> ids = Collections.synchronizedList(new ArrayList<>());


    public static CountDownLatch countDownLatch = new CountDownLatch(100);



    public static void lock(){
        reentrantLock.lock();
    }



    public static void unlock(){
        reentrantLock.unlock();
    }


    public static void addId(String id){
        countDownLatch.countDown();
        ids.add(id);
    }


    public static void echoIds(){
        System.out.println(ids);
    }



}
