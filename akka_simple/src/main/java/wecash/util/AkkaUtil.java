package wecash.util;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import wecash.actors.HelloActor;


/**
 * Created by MrDing
 * Date: 2017/2/9.
 * Time:23:24
 */
public class AkkaUtil {

    private static ActorSystem sysem= ActorSystem.create("mySystem");

    /**
     * 创建actor引用
     * @param time Actor构造函数的参数
     * @return 生成的actor引用
     */
    public static ActorRef createActor(long time){
        return sysem.actorOf(Props.create(HelloActor.class,time));
    }

}
