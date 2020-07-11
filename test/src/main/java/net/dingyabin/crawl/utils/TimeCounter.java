package net.dingyabin.crawl.utils;

import net.dingyabin.crawl.Start;

import java.math.BigDecimal;

/**
 * Created by MrDing
 * Date: 2020/7/11.
 * Time:16:23
 */
public class TimeCounter {

    private static final int HOUR = 60 * 60 * 1000;

    private static final int MINUTE = 60 * 1000;

    private static final int SECOND = 1000;

    private static long preTime = System.currentTimeMillis();

    private static long curSpeed = 1;


    public static void refresh() {
        synchronized (TimeCounter.class) {
            long now = System.currentTimeMillis();
            curSpeed = now - preTime;
            preTime = now;
        }
    }


    public static String restTime() {
        long restTime = new BigDecimal(Start.getQUEUE().size()).multiply(new BigDecimal(curSpeed)).longValue();
        long hour = restTime / HOUR;
        long minute = (restTime % HOUR) / MINUTE;
        long second = (restTime % MINUTE) / SECOND;
        return String.format("%s小时%s分%s秒", hour, minute, second);
    }


}
