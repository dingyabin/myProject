package net.dingyabin.test;

import java.rmi.Remote;
import java.util.*;

/**
 * Created by MrDing
 * Date: 2018/6/24.
 * Time:11:26
 */
public class RedPackage {


    public static void main(String[] args) {

        List<Integer> grab = grab2(100, 5);
        for (Integer integer : grab) {
            System.out.println(integer);
        }
    }


    public static List<Integer> grab(int money, int people) {

        int restmoney = money;
        int restpeople = people;

        List<Integer> list = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < people - 1; i++) {
            int mm = random.nextInt(2 * restmoney / restpeople - 1) + 1;
            list.add(mm);
            restmoney -= mm;
            restpeople--;
        }
        list.add(restmoney);
        return list;
    }

    public static List<Integer> grab2(int money, int people) {
        LinkedList<Integer> list = new LinkedList<>();
        List<Integer> list2 = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < people - 1; i++) {
            int mm = random.nextInt(money - 1) + 1;
            list.add(mm);
        }
        list.addFirst(0);
        list.addLast(money);
        Collections.sort(list);

        for (int i = 0; i < list.size()-1; i++) {

            list2.add(list.get(i+1)-list.get(i));
        }
        return list2;
    }


}
