package net.dingyabin.com.enums;

/**
 * Created by MrDing
 * Date: 2017/2/18.
 * Time:11:41
 */
public enum Sex {

    MAN("01"),WOMEN("02");

    private String sex;

    Sex(String sex){
        this.sex=sex;
    }

    public  String getValue(){
        return this.sex;
    }

    public static Sex getByValue(String number){
        for (Sex s:values()){
           if (s.getValue().equals(number)){
               return s;
           }
        }
        return null;
    }

}
