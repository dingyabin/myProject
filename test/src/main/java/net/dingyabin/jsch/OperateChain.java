package net.dingyabin.jsch;

import com.jcraft.jsch.Channel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MrDing
 * Date: 2019/5/16.
 * Time:2:16
 */
public class OperateChain extends AbstractOperate {

    private List<AbstractOperate> list;


    public OperateChain() {
        this.list = new ArrayList<>();
    }

    public OperateChain addOperate(AbstractOperate abstractOperate) {
        list.add(abstractOperate);
        return this;
    }


    public boolean doPreHandle(Channel channel, List<String> commands) {
        for (AbstractOperate abstractOperate : list) {
            boolean result = abstractOperate.preHandle(channel, commands);
            if (!result) {
                return false;
            }
        }
        return true;
    }


    @Override
    protected boolean preHandle(Channel channel, List<String> commands) {
        return doPreHandle(channel, commands);
    }
}
