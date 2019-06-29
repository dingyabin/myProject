package net.dingyabin.jsch;

import com.jcraft.jsch.Channel;

import java.util.List;

/**
 * Created by MrDing
 * Date: 2019/5/16.
 * Time:2:09
 */
public abstract class AbstractOperate {


    protected abstract boolean preHandle(Channel channel, List<String> commands);


}
