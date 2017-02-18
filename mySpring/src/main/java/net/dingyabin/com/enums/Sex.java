package net.dingyabin.com.enums;

/**
 * Created by MrDing
 * Date: 2017/2/18.
 * Time:11:41
 */
public enum Sex {

    MAN(1),WOMEN(2);

    private Integer sex;

    Sex(Integer sex){
        this.sex=sex;
    }

    public  Integer getValue(){
        return this.sex;
    }

    public static Sex getByValue(Integer number){
        for (Sex s:values()){
           if (s.getValue().equals(number)){
               return s;
           }
        }
        return null;
    }
}
