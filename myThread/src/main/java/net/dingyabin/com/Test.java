package net.dingyabin.com;

import org.joda.time.DateTime;
import org.joda.time.Months;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by MrDing
 * Date: 2017/6/23.
 * Time:22:58
 */
public class Test {


    public static void main(String[] args) {
        String ss = "次准备数据结果:          isSuccess       =          false  ,   false发eqwrqrq false";
        Pattern compile = Pattern.compile("isSuccess[\\s]*=[\\s]*(true|false)");
        Matcher matcher = compile.matcher(ss);
        while (matcher.find()) {
            System.out.println(matcher.group(1));
        }

        DateTime startTime = new DateTime("2011-03-11");
        DateTime endTime = new DateTime("2011-03-11");

        if (startTime.isAfter(endTime)){
            System.out.println("xxxxxxxxxxxxxxxxx");
            return;
        }

        while (true) {
            String tableName = "client_query_record_" + startTime.toString("yyMM");
            System.out.println(tableName);
            if(Objects.equals(startTime.toString("yyMM"), endTime.toString("yyMM"))){
                break;
            }
            startTime = startTime.plusMonths(1);
        }
    }
}
