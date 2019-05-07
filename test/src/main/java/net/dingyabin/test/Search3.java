package net.dingyabin.test;

import java.util.*;

/**
 * Created by MrDing
 * Date: 2019/3/12.
 * Time:16:03
 */
public class Search3 {

    private List<String> sourceKey = Arrays.asList("shandong", "dashandong", "shanshandong", "wudanshan");


    public void search(String keyWord) {
        char[] chars = keyWord.toUpperCase().toCharArray();
        StringBuilder regex = new StringBuilder(".*");
        for (char aChar : chars) {
            regex.append(aChar).append(".*");
        }
        System.out.println(regex);
        for (String s : sourceKey) {
            if (s.toUpperCase().matches(regex.toString())){
                System.out.println(s);
            }
        }
    }


    public static void main(String[] args) {
        Search3 search = new Search3();
        search.search("ssg");
    }



}
