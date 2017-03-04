package net.dingyabin.com.listeners;


import net.dingyabin.com.bean.Student;
import net.dingyabin.com.consumer.BaseConsumer;
import org.springframework.amqp.core.MessageProperties;


/**
 * Created by MrDing
 * Date: 2017/3/4.
 * Time:22:36
 */
public class StringConsumer extends BaseConsumer<String> {

    @Override
    protected void process(String str, MessageProperties messageProperties) {
        try {
            Thread.sleep(1000);
            System.out.println("接收到消息"+str);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
