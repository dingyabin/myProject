package net.dingyabin.com.test;

import net.dingyabin.com.annotation.MyAnnotation;
import org.springframework.stereotype.Component;

/**
 * Created by MrDing
 * Date: 2017/7/8.
 * Time:10:31
 */

@Component
@MyAnnotation(name="dingyabin")
public class ConsumerClass extends  AbstractClass {

    @Override
    public void handle() {
        System.out.println("handle..................");
    }
}
