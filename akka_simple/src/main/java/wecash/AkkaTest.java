package wecash;

import akka.actor.ActorRef;
import wecash.util.AkkaUtil;

/**
 * Created by MrDing on 2017/2/9.
 */
public class AkkaTest {

    public static void main(String[] args) {
        for (int i = 0; i <5 ; i++) {
            ActorRef actor = AkkaUtil.createActor(i * 1000);
            actor.tell(String.format("msg_%s",i), ActorRef.noSender());
        }
        System.out.println("main方法完毕.....");
    }

}
