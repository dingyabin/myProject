package net.dingyabin.crawl;

import net.dingyabin.crawl.factory.ProducerFactory;
import net.dingyabin.crawl.model.Torrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static net.dingyabin.crawl.enums.WebSiteEnum.CILICAO;


/**
 * Created by MrDing
 * Date: 2018/7/28.
 * Time:22:24
 */
public class Start {

    private static final LinkedBlockingQueue<Torrent> QUEUE = new LinkedBlockingQueue<>();

    private static final AtomicInteger INDEX = new AtomicInteger();

    private static final ExecutorService PRODUCER_EXECUTOR = Executors.newCachedThreadPool(r -> {
        Thread thread = new Thread(r);
        thread.setName("producer task thread-" + INDEX.getAndIncrement());
        return thread;
    });

    private static final ExecutorService CONSUMER_EXECUTOR = Executors.newCachedThreadPool(r -> {
        Thread thread = new Thread(r);
        thread.setName("consumer task thread-" + INDEX.getAndIncrement());
        return thread;
    });



    public static void main(String[] args) throws InterruptedException {
        for (int i = 1; i <= 22; i++) {
            PRODUCER_EXECUTOR.submit(ProducerFactory.getProducer(CILICAO, QUEUE, i));
        }
        //生产者线程池关闭
        PRODUCER_EXECUTOR.shutdown();


        for (int i = 0; i < 1; i++) {
            CONSUMER_EXECUTOR.submit(CILICAO.consumer().setWebSiteEnum(CILICAO).setQueue(QUEUE));
        }
        //消费者线程池关闭
        CONSUMER_EXECUTOR.shutdown();

        //等待
        awaitTermination(PRODUCER_EXECUTOR, CONSUMER_EXECUTOR);

        System.out.println("^_^_^_^_^_^^_^_^任务完成^_^^_^_^^_^_^^_^_^");
    }


    public static LinkedBlockingQueue<Torrent> getQUEUE() {
        return QUEUE;
    }


    /**
     * 等待线程池任务全部完成
     * @param executorServices 线程池
     * @throws InterruptedException 异常
     */
    private static void awaitTermination(ExecutorService... executorServices) throws InterruptedException {
        for (ExecutorService executorService : executorServices) {
            executorService.awaitTermination(10, TimeUnit.HOURS);
        }
    }


    /**
     * 生产线程池任务是否全部完成
     * @return 生产线程池任务是否全部完成
     */
    public static boolean iProducerExecutorFinished(){
       return PRODUCER_EXECUTOR.isTerminated();
    }

}
