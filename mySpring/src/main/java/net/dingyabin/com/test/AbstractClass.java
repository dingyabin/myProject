package net.dingyabin.com.test;

import net.dingyabin.com.annotation.MyAnnotation;
import org.springframework.beans.factory.InitializingBean;

/**
 * Created by MrDing
 * Date: 2017/7/8.
 * Time:10:15
 */

public abstract class AbstractClass implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        boolean annotationPresent = this.getClass().isAnnotationPresent(MyAnnotation.class);
        if (annotationPresent) {
            MyAnnotation annotation = this.getClass().getAnnotation(MyAnnotation.class);
            System.out.println("afterPropertiesSet:name=" + annotation.name());
        }
        handle();
    }

    public abstract void handle();

}
