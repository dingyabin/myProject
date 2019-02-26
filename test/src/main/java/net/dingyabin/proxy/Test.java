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

        String s = "abcabcbb";

        int[] m = new int[256];

        int res = 0, left = 0;

        for (int i = 0; i < s.length(); i++) {
            left = Math.max(left, m[s.charAt(i)]);

            res = Math.max(res, i - left + 1);

            m[s.charAt(i)] = i + 1;
        }
        System.out.println(res);





//        TimeProxy timeProxy=new TimeProxy(car);
//        MoveAble move = (MoveAble) Proxy.newProxyInstance(car.getClass().getClassLoader(), car.getClass().getInterfaces(), timeProxy);
//        System.out.println(move.getClass().getName());
//        move.move();
    }
}
