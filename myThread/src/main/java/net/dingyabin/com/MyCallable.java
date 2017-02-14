package net.dingyabin.com;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by MrDing
 * Date: 2017/2/14.
 * Time:21:31
 */
public class MyCallable implements Callable<String> {

    private int index;

    private AtomicInteger atomicInteger;

    public MyCallable(int index, AtomicInteger atomicInteger) {
        this.index = index;
        this.atomicInteger = atomicInteger;
    }

    @Override
    public String call() throws Exception {
        atomicInteger.getAndIncrement();
        System.out.println(Thread.currentThread().getName()+"开始执行任务"+index);
        if(index==2){
            throw new Exception(Thread.currentThread().getName()+"线程发生异常"+index);
        }
        Thread.sleep(3000);
        return Thread.currentThread().getName()+"执行任务完毕"+index;
    }
}
