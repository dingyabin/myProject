package net.dingyabin.com.service.impl;

import net.dingyabin.com.bean.Student;
import net.dingyabin.com.service.StudentProducer;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by MrDing
 * Date: 2017/3/4.
 * Time:22:59
 */
@Service("studentProducer")
public class StudentProducerImpl implements StudentProducer{

    @Resource
    private AmqpTemplate amqpTemplate;

    @Override
    public void send(String key, Student student) {
        amqpTemplate.convertAndSend(key,student);
    }

    @Override
    public void send(String key, String value) {
        amqpTemplate.convertAndSend(key,value);
    }

}
