package net.dingyabin.crawl;

import cn.hutool.core.thread.NamedThreadFactory;
import net.dingyabin.crawl.factory.ProducerFactory;
import net.dingyabin.crawl.model.Torrent;

import java.util.concurrent.*;

import static net.dingyabin.crawl.enums.WebSiteEnum.TAI91;


/**
 * Created by MrDing
 * Date: 2018/7/28.
 * Time:22:24
 */
public class Start {

    private static final LinkedBlockingQueue<Torrent> QUEUE = new LinkedBlockingQueue<>();

    private static final ExecutorService PRODUCER_EXECUTOR = Executors.newFixedThreadPool(
            5,
            new NamedThreadFactory("producer task thread-", false)
    );


    private static final ThreadPoolExecutor CONSUMER_EXECUTOR = new ThreadPoolExecutor(
            5,
            10,
            1,
            TimeUnit.MINUTES,
            new ArrayBlockingQueue<>(1000000),
            new NamedThreadFactory("consumer task thread-", false),
            new ThreadPoolExecutor.CallerRunsPolicy()
    );

    public static void main(String[] args) throws InterruptedException {
        for (int i = 950; i <= 1000; i++) {
            PRODUCER_EXECUTOR.submit(ProducerFactory.getProducer(TAI91, QUEUE, i));
        }
        //生产者线程池关闭
        PRODUCER_EXECUTOR.shutdown();


        for (int i = 0; i < 5; i++) {
            CONSUMER_EXECUTOR.submit(TAI91.consumer().setWebSiteEnum(TAI91).setQueue(QUEUE));
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


    public static int getRestTaskCount(){
        return CONSUMER_EXECUTOR.getQueue().size() + CONSUMER_EXECUTOR.getActiveCount() + QUEUE.size();
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
