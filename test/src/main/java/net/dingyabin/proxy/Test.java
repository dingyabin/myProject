package net.dingyabin.proxy;

import java.lang.reflect.Proxy;

/**
 * Created by MrDing
 * Date: 2018/4/3.
 * Time:22:32
 */
public class Test {

    public static void main(String[] args) {
        Car car=new Car();
        TimeProxy timeProxy=new TimeProxy(car);
        MoveAble move = (MoveAble) Proxy.newProxyInstance(car.getClass().getClassLoader(), car.getClass().getInterfaces(), timeProxy);
        System.out.println(move.getClass().getName());
        move.move();
    }
}
