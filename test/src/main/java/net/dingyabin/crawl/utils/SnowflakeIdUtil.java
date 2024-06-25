package net.dingyabin.crawl.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

/**
 * @author 丁亚宾
 * Date: 2024/6/25.
 * Time:23:34
 */
public class SnowflakeIdUtil {

    private static Snowflake snowflake;

    static {
        Long workIdByDb = DbUtil.getWorkIdByDb();
        if (workIdByDb != null) {
            snowflake = IdUtil.getSnowflake(workIdByDb);
        } else {
            snowflake = IdUtil.getSnowflake();
        }
    }

    public static long nextId() {
        return snowflake.nextId();
    }


    public static void main(String[] args) {
        long l = SnowflakeIdUtil.nextId();
        System.out.println(l);
         l = SnowflakeIdUtil.nextId();
        System.out.println(l);
    }


}
