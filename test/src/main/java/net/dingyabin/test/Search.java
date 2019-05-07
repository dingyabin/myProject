package net.dingyabin.test;

import java.util.*;

/**
 * Created by MrDing
 * Date: 2019/3/12.
 * Time:16:03
 */
public class Search {

    private List<String> sourceKey = Arrays.asList("shandong", "dashandong", "shanshandong", "wudanshan");


    public void search(String keyWord) {
        char[] chars = keyWord.toUpperCase().toCharArray();
        for (String source : sourceKey) {
            int startIndex = 0;
            boolean match = true;
            String upperSource = source.toUpperCase();
            StringBuilder sb = new StringBuilder(source);
            for (int i = 0; i < chars.length; i++) {
                int currentIndex = upperSource.indexOf(String.valueOf(chars[i]), startIndex);
                if (currentIndex < 0) {
                    match = false;
                    break;
                }
                startIndex = currentIndex + 1;
                //这里的13是因为每增加一个<font></font>标签会增加13个字符
                sb.replace(i * 13 + currentIndex, i * 13 + currentIndex + 1, String.format("<font>%s</font>", source.charAt(currentIndex)));
            }
            if (match) {
                System.out.println(sb.toString());
            }
        }
    }


    public static void main(String[] args) {
        Search search = new Search();
        search.search("ssd");
    }


}
