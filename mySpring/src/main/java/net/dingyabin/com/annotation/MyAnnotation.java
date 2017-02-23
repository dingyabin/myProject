package net.dingyabin.com.annotation;

import net.dingyabin.com.enums.Sex;

import java.lang.annotation.*;

/**
 * Created by MrDing
 * Date: 2017/2/20.
 * Time:22:50
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)//该Annotation被保留的时间长短
@Target({ElementType.TYPE,ElementType.METHOD,ElementType.PARAMETER})//注解的使用位置
@Inherited//可以被继承
public @interface MyAnnotation {

    public String name()  default "";

    public Sex sex() default Sex.MAN;

}
