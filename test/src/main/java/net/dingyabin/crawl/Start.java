package net.dingyabin.crawl;

import net.dingyabin.crawl.consumer.SimpleTorrentConcumer;
import net.dingyabin.crawl.factory.ProducerFactory;
import net.dingyabin.crawl.model.Torrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static net.dingyabin.crawl.enums.WebSiteEnum.*;


/**
 * Created by MrDing
 * Date: 2018/7/28.
 * Time:22:24
 */
public class Start {

    private static final LinkedBlockingQueue<Torrent> QUEUE = new LinkedBlockingQueue<>();

    private static final String BATHPATH = String.format("C:\\Users\\%s\\Desktop\\torrent\\",System.getenv().get("USERNAME"));

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 1; i <= 10; i++) {
            executorService.submit(ProducerFactory.getProducer(DYTT, QUEUE, i));
        }
//        for (int i = 0; i < 10; i++) {
//            executorService.submit(new SimpleTorrentConcumer(QUEUE, BATHPATH));
//        }
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.HOURS);
        System.out.println("^_^_^_^_^_^^_^_^任务完成^_^^_^_^^_^_^^_^_^");
    }
}
