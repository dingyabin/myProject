package net.dingyabin.com.scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务类
 * Created by MrDing
 * Date: 2017/2/13.
 * Time:22:21
 */
@Component
public class MyTask {

    Logger logger = LoggerFactory.getLogger(MyTask.class);

    public static ThreadLocal<Integer> times = new ThreadLocal<>();

    @Scheduled(cron = "0/5 * *  * * ? ")
    public void run() {
        Integer current = times.get();
        current = (current == null ? 1 : current + 1);
        System.out.println("现在在执行定时任务,任务已执行了" + current + "次.......");
        times.set(current);
    }

}
