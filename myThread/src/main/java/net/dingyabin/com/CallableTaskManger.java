package net.dingyabin.com;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.time.StopWatch;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Created by MrDing
 * Date: 2017/2/14.
 * Time:21:39
 */
public class CallableTaskManger {

    public static void main(String[] args) {
        StopWatch watch = StopWatch.createStarted();
        AtomicInteger atomicInteger = new AtomicInteger(0);
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Future<String>> list = Lists.newArrayList();
        for (int i = 0; i < 155; i++) {
            Future<String> future = executorService.submit(new MyCallable(i, atomicInteger));
            list.add(future);
        }
        executorService.shutdown();
        for (Future<String> f : list) {
            try {
                String s = f.get();
                System.out.println("执行结果是：" + s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(String.format("main方法执行完毕,atomicInteger=%s,共耗时：%s ms", atomicInteger.get(), watch.getTime()));
    }
}
