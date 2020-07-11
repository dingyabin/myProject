package net.dingyabin;

import java.util.Arrays;

/**
 * Created by MrDing
 * Date: 2020/6/1.
 * Time:14:48
 */
public class Test58 {


    public static void main(String[] args) {

        int[] array = {2, 1, 5, 4, 7, 8, 9, 22, 11, 3, 98};

        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                if (array[j] % 2 == 0 && array[j + 1] % 2 == 1) {
                    int tmp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = tmp;
                }
            }
        }
        System.out.println(Arrays.toString(array));


    }


}
