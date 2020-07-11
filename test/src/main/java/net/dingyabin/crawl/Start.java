package net.dingyabin.crawl;

import net.dingyabin.crawl.consumer.SimpleTorrentConcumer;
import net.dingyabin.crawl.factory.ProducerFactory;
import net.dingyabin.crawl.model.Torrent;
import net.dingyabin.crawl.utils.TimeCounter;

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

    private static final LinkedBlockingQueue<Torrent> QUEUE = new LinkedBlockingQueue<Torrent>() {
        private static final long serialVersionUID = 7487139222835543212L;
        @Override
        public Torrent poll(long timeout, TimeUnit unit) throws InterruptedException {
            Torrent torrent = super.poll(timeout, unit);
            TimeCounter.refresh();
            return torrent;
        }
    };

    public static void main(String[] args) throws InterruptedException {
        AtomicInteger index = new AtomicInteger();
        ExecutorService executorService = Executors.newFixedThreadPool(10, r -> {
            Thread thread = new Thread(r);
            thread.setName("task thread-" + index.getAndIncrement());
            return thread;
        });
        for (int i = 1; i <= 1; i++) {
            executorService.submit(ProducerFactory.getProducer(M3U8, QUEUE, i));
            TimeUnit.SECONDS.sleep(1);
        }
        for (int i = 0; i < 10; i++) {
            executorService.submit(new SimpleTorrentConcumer(M3U8, QUEUE));
        }
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.HOURS);
        System.out.println("^_^_^_^_^_^^_^_^任务完成^_^^_^_^^_^_^^_^_^");
    }


    public static LinkedBlockingQueue<Torrent> getQUEUE() {
        return QUEUE;
    }

}
