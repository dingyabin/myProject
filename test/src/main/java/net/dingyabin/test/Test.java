package net.dingyabin.test;

import net.dingyabin.bean.Weight;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

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


        DateTime parse = DateTime.parse("2019-03-08", DateTimeFormat.forPattern("yyyy-MM-dd"));
        int days = Days.daysBetween(parse, DateTime.now()).getDays();
        System.out.println(days);

    }
}
