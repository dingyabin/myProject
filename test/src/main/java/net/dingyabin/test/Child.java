package net.dingyabin.test;

/**
 * Created by MrDing
 * Date: 2018/6/20.
 * Time:23:50
 */
public class Child extends Parent {

    @Override
    public void a() {
        super.a();
    }

    @Override
    public void b() {
        System.out.println("****************b");
    }

    public static void main(String[] args) {
        new Child().a();
    }
}
