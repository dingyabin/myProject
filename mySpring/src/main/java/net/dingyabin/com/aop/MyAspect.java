package net.dingyabin.com.aop;

import net.dingyabin.com.annotation.MyAnnotation;
import net.dingyabin.com.bean.Student;
import net.dingyabin.com.enums.Sex;
import org.aspectj.lang.JoinPoint;


/**
 * Created by MrDing
 * Date: 2017/2/19.
 * Time:12:41
 */

public class MyAspect {

    public void before(JoinPoint point,Student student){
        String className = point.getTarget().getClass().getName();
        String methodName = point.getSignature().getName();
        Object[] args = point.getArgs();
        System.out.println("aop的befroe方法执行......");
        System.out.println(String.format("className=%s, methodName=%s ,参数为:%s",className,methodName,args[0]));
        System.out.println("参数值："+student);
    }

    public void afterReturn(JoinPoint point,Student student){
        String className = point.getTarget().getClass().getName();
        String methodName = point.getSignature().getName();
        Object[] args = point.getArgs();
        System.out.println("aop的afterReturn方法执行......");
        System.out.println(String.format("className=%s, methodName=%s ,参数为:%s",className,methodName,args[0]));
        System.out.println("返回值："+student);

    }


}
