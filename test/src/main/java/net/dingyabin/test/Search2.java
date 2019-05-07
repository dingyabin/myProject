package net.dingyabin.test;

import java.util.*;

/**
 * Created by MrDing
 * Date: 2019/3/12.
 * Time:16:03
 */
public class Search2 {

    private List<String> sourceKey = Arrays.asList("shandong", "dashandong", "shanshandong", "wudanshan");

    private List<Map<String, List<Integer>>> indexMapList;


    public Search2() {
        indexMapList = new ArrayList<>();
        for (String source : sourceKey) {
            char[] chars = source.toUpperCase().toCharArray();
            Map<String, List<Integer>> map = new HashMap<>();
            for (int i = 0; i < chars.length; i++) {
                String charStr = String.valueOf(chars[i]);
                if (map.containsKey(charStr)) {
                    map.get(charStr).add(i);
                    continue;
                }
                List<Integer> list = new ArrayList<>();
                list.add(i);
                map.put(charStr, list);
            }
            indexMapList.add(map);
        }
    }


    public void search(String keyWord) {
        char[] chars = keyWord.toUpperCase().toCharArray();
        for (int i = 0; i < indexMapList.size(); i++) {
            Map<String, List<Integer>> map = indexMapList.get(i);
            boolean match = true;
            int startIndex = 0;
            for (char aChar : chars) {
                String charStr = String.valueOf(aChar);
                if (!map.containsKey(charStr)) {
                    match = false;
                    break;
                }
                List<Integer> integers = map.get(charStr);
                int finalIndex = startIndex;
                Optional<Integer> first = integers.stream().filter(index -> index >= finalIndex).findFirst();
                match = first.isPresent();
                if (!match) {
                    break;
                }
                startIndex = first.get() + 1;
            }
            if (match) {
                System.out.println(sourceKey.get(i));
            }
        }
    }


    public static void main(String[] args) {
//        Search2 search = new Search2();
//        search.search("ssg");

        System.out.println("12wudaanshan".matches(".*w.*aa.*n.*"));
    }


}
