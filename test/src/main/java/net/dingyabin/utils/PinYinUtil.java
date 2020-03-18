package net.dingyabin.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.lang3.RandomUtils;

/**
 * Created by MrDing
 * Date: 2019/10/26.
 * Time:0:43
 */
public class PinYinUtil {


    public static String getPinYinHeadChar(String str) throws BadHanyuPinyinOutputFormatCombination {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
        StringBuilder convert = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char word = str.charAt(i);
            //提取汉字的首字母
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word,format);
            if (pinyinArray != null && pinyinArray.length > 0) {
                convert.append(pinyinArray[0].charAt(0));
            } else {
                convert.append(word);
            }
        }
        return convert.toString();
    }

    public static void main(String[] args) throws BadHanyuPinyinOutputFormatCombination {
//        System.out.println(getPinYinHeadChar("1放进的算法"));
        Long userID = RandomUtils.nextLong(0, 500000);
        System.out.println(userID);
        String s =  ""+ System.currentTimeMillis() + RandomUtils.nextInt(1000, 9999) + (userID< 100000 ? String.format("%06d",userID):String.format("%06d",userID % 100000)) ;
        System.out.println(s);
    }



}