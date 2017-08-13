package net.dingyabin.com.test;


import net.dingyabin.com.bean.Student;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

/**
 * Created by MrDing
 * Date: 2017/7/23.
 * Time:21:12
 */
public class Test {

    public static void main(String[] args) throws Exception {
//
//        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
//        ObjectOutputStream objectOutputStream=new ObjectOutputStream(new FileOutputStream("C:\\Users\\MrDing\\Desktop\\a.txt"));
//        objectOutputStream.writeObject(new Student(1,"2422",null,null));
//        objectOutputStream.close();


//        ObjectInputStream o = new ObjectInputStream(new FileInputStream("C:\\Users\\MrDing\\Desktop\\a.txt"));
//
//        System.out.println(o.readObject());

//        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date(1501244225260L)));

        Properties properties=new Properties();
        properties.load(Test.class.getResourceAsStream("/log4j.properties"));
        for (Map.Entry<Object, Object> objectObjectEntry : properties.entrySet()) {
            System.out.println(objectObjectEntry.getKey()+"---"+objectObjectEntry.getValue());
        }


    }


}
