package net.dingyabin.test;

/**
 * Created by MrDing
 * Date: 2018/6/20.
 * Time:23:49
 */
public class Parent {

    public  void a (){
        System.out.println("====a");
        Parent ss=this;
        ss.b();
    }


    public  void b (){
        System.out.println("====b");
    }
}
