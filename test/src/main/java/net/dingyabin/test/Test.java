package net.dingyabin.test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MrDing
 * Date: 2017/9/13.
 * Time:21:05
 */
public class Test {

    public static void main(String[] args) {
        List<Long> list =new ArrayList<>();
        list.add(1L);
        list.add(2L);
        list.add(3L);
        System.out.println(list.contains(1L));
    }
}
