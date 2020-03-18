package net.dingyabin.crawl;

import net.dingyabin.crawl.consumer.SimpleTorrentConcumer;
import net.dingyabin.crawl.factory.ProducerFactory;
import net.dingyabin.crawl.model.Torrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static net.dingyabin.crawl.enums.WebSiteEnum.*;


/**
 * Created by MrDing
 * Date: 2018/7/28.
 * Time:22:24
 */
public class Start {

    private static final LinkedBlockingQueue<Torrent> QUEUE = new LinkedBlockingQueue<>();

    public static void main(String[] args) throws InterruptedException {
        AtomicInteger index = new AtomicInteger();
        ExecutorService executorService = Executors.newFixedThreadPool(10, r -> {
            Thread thread = new Thread(r);
            thread.setName("task thread-" + index.getAndIncrement());
            return thread;
        });
        for (int i = 1; i <= 8; i++) {
            executorService.submit(ProducerFactory.getProducer(LPXXS, QUEUE, i));
            TimeUnit.SECONDS.sleep(1);
        }
        for (int i = 0; i < 10; i++) {
            executorService.submit(new SimpleTorrentConcumer(LPXXS, QUEUE));
        }
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.HOURS);
        System.out.println("^_^_^_^_^_^^_^_^任务完成^_^^_^_^^_^_^^_^_^");
    }
}
