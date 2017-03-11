package net.dingyabin.com.utils;

import org.apache.commons.codec.binary.Base64;

import java.io.FileInputStream;


/**
 * Created by MrDing
 * Date: 2017/3/11.
 * Time:0:31
 */
public class DecoderUtil {


    public static void main(String[] args) {
        String str="3333fghfghgf33534563464536";
        String str64 = Base64.encodeBase64String(str.getBytes());
        System.out.println("加密后："+str64);

        byte[] bytes1 = Base64.decodeBase64(str64);
        String origin = new String(bytes1);
        System.out.println("加密前："+origin);

        try {
            FileInputStream input=new FileInputStream("C:\\Users\\MrDing\\Desktop\\111.PNG");
            byte[] array=new byte[input.available()];
            input.read(array);
            String str64s= Base64.encodeBase64String(array);
            input.close();
            System.out.println("图片编码后长度："+str64s.length());
            System.out.println("图片编码后："+str64s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
