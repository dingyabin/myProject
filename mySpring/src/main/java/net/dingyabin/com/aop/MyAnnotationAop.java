package net.dingyabin.com.aop;

import net.dingyabin.com.annotation.MyAnnotation;
import net.dingyabin.com.enums.Sex;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by MrDing
 * Date: 2017/2/20.
 * Time:23:19
 */
@Component
@Aspect
public class MyAnnotationAop {


    @Pointcut(value = "execution(* net.dingyabin.com.service.impl.MyServiceImpl.*(..))")
    public void myPointCut() {
    }


    @Before(value = "myPointCut()")
    public void before(JoinPoint point) {
        Class<?> aClass = point.getTarget().getClass();
        Method method = getMethod(point, aClass);
        if (method != null) {
            Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        }
        boolean annotationPresent = aClass.isAnnotationPresent(MyAnnotation.class);
        if (annotationPresent) {
            MyAnnotation annotation = aClass.getAnnotation(MyAnnotation.class);
            String name = annotation.name();
            Sex sex = annotation.sex();
            System.out.println(String.format("-----------发现注解,name=%s ,sex=%s------------", name, sex));
            Object[] args = point.getArgs();
            args[0] = "*************";
            System.out.println("修改参数为: ***************");
        }

    }


    /**
     * 在aClass中找point的方法
     *
     * @param point  切点
     * @param aClass 目标类
     * @return 找到的方法（可能为null）
     */
    private Method getMethod(JoinPoint point, Class aClass) {
        Method m = ((MethodSignature) point.getSignature()).getMethod();
        try {
            return aClass.getMethod(point.getSignature().getName(), m.getParameterTypes());
        } catch (NoSuchMethodException e) {
            return null;
        }
    }


}
