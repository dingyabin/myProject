package net.dingyabin.com;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.*;
import org.apache.commons.lang3.time.StopWatch;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by MrDing
 * Date: 2017/3/11.
 * Time:18:39
 */
public class GuavaTaskManager {

    public static void main(String[] args) {
        StopWatch started = StopWatch.createStarted();
        AtomicInteger atomicInteger = new AtomicInteger(0);
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        ExecutorService listenerService = Executors.newSingleThreadExecutor();
        ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(executorService);
        List<ListenableFuture<String>> list = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            ListenableFuture<String> listenableFuture = listeningExecutorService.submit(new MyCallable(i, atomicInteger));
            list.add(listenableFuture);
        }

        /* 异步回调方式一 ：listenableFuture.addListener()方法*/
        for (ListenableFuture<String> l : list) {
            final ListenableFuture<String> listenableFuture = l;
            listenableFuture.addListener(new Runnable() {
                @Override
                public void run() {
                    try {
                        String s = listenableFuture.get();
                        System.out.println("result:-------  "+s);
                    } catch (Exception e) {
                        System.out.println("异常了............."+e.getMessage());
                    }
                }
            }, listenerService);
        }

        /* 异步回调方式二 ： Futures.addCallback()方法，可以针对成功和失败分别做处理*/
        for (ListenableFuture<String> listenableFuture : list) {
            Futures.addCallback(listenableFuture, new FutureCallback<String>() {
                @Override
                public void onSuccess(String s) {
                    System.out.println("result:-------  "+s);
                }

                @Override
                public void onFailure(Throwable e) {
                    System.out.println("异常了............."+e.getMessage());
                }
            });
        }
        listeningExecutorService.shutdown();
        System.out.println("main方法执行完毕。。。。。。耗时："+started.getTime());
    }

}



