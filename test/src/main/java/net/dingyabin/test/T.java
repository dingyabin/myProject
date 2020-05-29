package net.dingyabin.test;

import jdk.nashorn.internal.runtime.CodeInstaller;
import lombok.ToString;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

/**
 * Created by MrDing
 * Date: 2020/5/19.
 * Time:17:29
 */

public class T {

    private int i = 555555;

    public ArrayList<T> list = new ArrayList<>();





    public static void main(String[] args) throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(1);
        countDownLatch.await();
        T test = new T();
        test.list.add(test);
        test.toString();
    }





}
