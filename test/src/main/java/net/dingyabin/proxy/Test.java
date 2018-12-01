package net.dingyabin.proxy;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by MrDing
 * Date: 2018/4/3.
 * Time:22:32
 */
public class Test {

    public static void main(String[] args) throws Exception {

        Method move = MoveAble.class.getMethod("test");
        Method move2 = Car.class.getMethod("test");

        System.out.println(move.equals(move2));






//        TimeProxy timeProxy=new TimeProxy(car);
//        MoveAble move = (MoveAble) Proxy.newProxyInstance(car.getClass().getClassLoader(), car.getClass().getInterfaces(), timeProxy);
//        System.out.println(move.getClass().getName());
//        move.move();
    }
}
