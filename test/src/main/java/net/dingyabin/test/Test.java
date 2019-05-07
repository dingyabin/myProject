package net.dingyabin.test;

import net.dingyabin.bean.Weight;

/**
 * Created by MrDing
 * Date: 2019/4/2.
 * Time:0:51
 */
public class Test {

    private static Test2 t2 = new Test2();

    public static void main(String[] args) {
        Weight weight=new Weight();
        weight.setId(1L);
        System.out.println("before:"+weight);
        t2.handle(weight);
        System.out.println("after:"+weight);

    }
}
