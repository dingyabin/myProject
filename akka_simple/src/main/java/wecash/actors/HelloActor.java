package wecash.actors;

import akka.actor.UntypedActor;

/**
 * Created by MrDing on 2017/2/9.
 */
public class HelloActor extends UntypedActor {

    private long delayTime;

    public HelloActor(long delayTime) {
        this.delayTime = delayTime;
    }

    @Override
    public void onReceive(Object msg) throws Exception {
        System.out.println("接收到消息,开始执行任务。。。。。"+msg);
        Thread.sleep(delayTime);
        System.out.println("任务执行完毕 "+msg);
        getContext().stop(getSelf());
    }
}
