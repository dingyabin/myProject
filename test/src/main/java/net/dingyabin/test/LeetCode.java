package net.dingyabin.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;


/**
 * Created by MrDing
 * Date: 2019/2/14.
 * Time:13:21
 */
@RunWith(JUnit4.class)
public class LeetCode {

    /**
     * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度
     *
     * @return
     */
    @Test
    public void lengthOfLongestSubstring() {
        String s = "abcscabc";
        int[] m = new int[256];
        int res = 0, left = 0;
        for (int i = 0; i < s.length(); i++) {
            left = Math.max(left, m[s.charAt(i)]);
            res = Math.max(res, i - left + 1);
            m[s.charAt(i)] = i + 1;
        }
        System.out.println(res);

        //方法2
//        HashMap<Character, Integer> hashMap = new HashMap<>();
//        int res = 0, left = 0;
//        for (int i = 0; i < s.length(); i++) {
//            if (hashMap.containsKey(s.charAt(i))) {
//                left = Math.max(left, hashMap.get(s.charAt(i)));
//            }
//            res = Math.max(res, i - left + 1);
//            hashMap.put(s.charAt(i), i + 1);
//        }
    }


    /**
     * 编写一个函数来查找字符串数组中的最长公共前缀。
     * 如果不存在公共前缀，返回空字符串 ""。
     */
    @Test
    public void longestCommonPrefix() {
        String[] strs = {"dogcecar", "dog", "dogcar"};
        String prefix = strs[0];// 默认将第一个认为是最长共同
        for (int i = 1; i < strs.length; i++) {
            // 将当前最长共同字符串和当前数组中的对比,把小的那个作为长度
            int length = prefix.length() > strs[i].length() ? strs[i].length() : prefix.length();
            int j = 0;
            for (; j < length; j++) {
                // 逐个字符比较,不等的时候退出
                if (prefix.charAt(j) != strs[i].charAt(j)) {
                    break;
                }
            }
            // 退出的j即当前的最小,整个遍历结束后就是整个的最小了
            prefix = prefix.substring(0, j);
        }
        System.out.println(prefix);
    }


    /**
     * 给定两个字符串 s1 和 s2，写一个函数来判断 s2 是否包含 s1 的排列。
     * 换句话说，第一个字符串的排列之一是第二个字符串的子串
     * 这道题不能用全排列的思路来做，否则一定会出现超时的情况，
     * 我们需要两个长度为26的哈希表memo1和memo2，
     * 用来记录s1和s2中每个字符出现的次数，
     * 我们维护一个长度为s1.size()的滑动窗口，
     * 逐渐从0到s2.size()滑动，如果滑动窗口内的memo2累计的在窗口内的各个字符对应次数等于memo1，
     * 那么返回true，否则返回false。
     * ////
     */
    @Test
    public void checkInclusion() {
        String s1 = "adc";
        String s2 = "dcda";
        boolean result = false;
        if (s1.length() > s2.length()) {
            System.out.println(false);
            return;
        }
        int[] m1 = new int[26];
        for (int i = 0; i < s1.length(); i++) {
            m1[s1.charAt(i)-'a']++;
        }
        for (int i = 0; i <= s2.length() - s1.length(); i++) {
            String substring = s2.substring(i, i + s1.length());
            int[] m2 = new int[26];
            for (int j = 0; j < substring.length(); j++) {
                m2[substring.charAt(j)-'a']++;
            }
            boolean equals = Arrays.equals(m1, m2);
            if (equals){
                result=true;
               break;
            }
        }
        System.out.println(result);
    }


}
