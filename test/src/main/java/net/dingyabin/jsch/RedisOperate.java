package net.dingyabin.jsch;

import com.google.common.collect.Lists;
import com.jcraft.jsch.Channel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MrDing
 * Date: 2019/5/16.
 * Time:2:11
 */
public class RedisOperate extends AbstractOperate {

    @Override
    protected boolean preHandle(Channel channel, List<String> objects) {
        ArrayList<String> l = Lists.newArrayList();
        l.add("cd /data");
        l.add("cd redis/redis-3.0.7");
        l.add("cat sentinel.conf");
        objects.addAll(0, l);
        return true;
    }
}
