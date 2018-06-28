package net.dingyabin.proxy;

/**
 * Created by MrDing
 * Date: 2018/4/3.
 * Time:22:26
 */
public class Car implements  MoveAble {

    @Override
    public void move() {
        System.out.println("start move.........");
        try {
            Thread.sleep(400);
            System.out.println("moving............");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("end moving........");
        MoveAble moveAble=this;
        System.out.println(moveAble);
        moveAble.test();
    }

    @Override
    public void test() {
        System.out.println("-----------test--------");
    }
}
