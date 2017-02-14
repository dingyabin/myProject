package net.dingyabin.com;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.time.StopWatch;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
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
        while(true){
            boolean isDone=true;
            for (Future future : list) {
                isDone = isDone && future.isDone();
            }
            if(isDone){
                break;
            }
        }
        executorService.shutdown();
        System.out.println(String.format("main方法执行完毕,atomicInteger=%s,共耗时：%s ms",atomicInteger.get(),watch.getTime()));
    }
}
