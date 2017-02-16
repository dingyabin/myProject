package net.dingyabin.com;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.time.StopWatch;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Created by MrDing
 * Date: 2017/2/14.
 * Time:21:39
 */
public class RunableTaskManger {

    public static void main(String[] args) {
        StopWatch watch = StopWatch.createStarted();
        AtomicInteger atomicInteger =new AtomicInteger(0);
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Future> list=Lists.newArrayList();
        for (int i = 0; i < 5; i++) {
            Future<?> future = executorService.submit(new MyRunable(i,atomicInteger));
            list.add(future);
        }
        executorService.shutdown();
        try {
            //等待线程执行完毕
            executorService.awaitTermination(60, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("任务出错.......");
        }
        System.out.println(String.format("main方法执行完毕,atomicInteger=%s,共耗时：%s ms",atomicInteger.get(),watch.getTime()));
    }
}
