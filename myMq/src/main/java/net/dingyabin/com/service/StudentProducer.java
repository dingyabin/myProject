package net.dingyabin.com.service;

import net.dingyabin.com.bean.Student;

/**
 * Created by MrDing
 * Date: 2017/3/4.
 * Time:22:56
 */
public interface StudentProducer {

    void send(String key,Student student);

    void send(String key,String value);

}
