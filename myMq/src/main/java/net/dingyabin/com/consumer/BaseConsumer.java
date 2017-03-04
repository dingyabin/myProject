package net.dingyabin.com.consumer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.utils.SerializationUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


/**
 * Created by MrDing
 * Date: 2017/3/4.
 * Time:23:57
 */
public abstract class BaseConsumer<T> implements MessageListener {


    protected abstract void process(T deserialize, MessageProperties messageProperties);


    @Override
    public void onMessage(Message message) {
        try {
            MessageProperties messageProperties = message.getMessageProperties();
            String str = new String(message.getBody(), "utf-8");
            Type tType = this.getClass().getGenericSuperclass();// 用 getClass(), 不能用 .class
            Type parameterizedTType = ((ParameterizedType) tType).getActualTypeArguments()[0];
            T deserialize ;
            switch (parameterizedTType.toString()) {
                case "class java.lang.String":
                    deserialize = (T) str;
                    break;
                case "class java.lang.Integer":
                    deserialize = (T) new Integer(str);
                    break;
                default:
                    deserialize = (T) SerializationUtils.deserialize(message.getBody());
            }
            process(deserialize, messageProperties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
