package net.dingyabin.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by MrDing
 * Date: 2018/4/3.
 * Time:22:28
 */
public class TimeProxy implements InvocationHandler {


    private Object car;

    public TimeProxy(Object car) {
        super();
        this.car = car;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(proxy.getClass());
        System.out.println("time start");
        Object invoke = method.invoke(car, args);
        System.out.println("time end");
        return invoke;
    }


}
