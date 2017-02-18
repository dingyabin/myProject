package net.dingyabin.com.utils;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Created by MrDing
 * Date: 2017/2/18.
 * Time:15:57
 */
public class Config {

    private static Properties properties;

    static {
        try {
            properties=new Properties();
            InputStream input = Config.class.getResourceAsStream("/config/properties/config.properties");
            properties.load(new InputStreamReader(input,"utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String getAsString(String key){
        String value=null;
        try {
            value=properties.getProperty(key);
        }catch (Exception e){
            e.printStackTrace();
        }
        return value;
    }


    public static String getAsString(String key, String defaultValue){
        String value=null;
        try {
            value=properties.getProperty(key,defaultValue);
        }catch (Exception e){
            e.printStackTrace();
        }
        return value;
    }


    public static Integer getAsInteger(String key){
        Integer value=null;
        try {
            String temp=properties.getProperty(key);
            value=Integer.valueOf(temp);
        }catch (Exception e){
            e.printStackTrace();
        }
        return value;
    }


    public static Integer getAsInteger(String key, Integer defaultValue){
        Integer value=null;
        try {
            String temp=properties.getProperty(key,defaultValue.toString());
            value=Integer.valueOf(temp);
        }catch (Exception e){
            e.printStackTrace();
        }
        return value;
    }


}
