package net.dingyabin.sort;

/**
 * Created by MrDing
 * Date: 2018/5/13.
 * Time:11:50
 */
public class Sort {


    public static void sort(int[] array) {
        int sum = 0;
        int k = array.length;
        for (int i = 0; i < array.length; i++) {
            int index = 0;

            for (int j = 1; j < k; j++) {
                sum++;
                if (array[j - 1] > array[j]) {
                    int temp = array[j - 1];
                    array[j - 1] = array[j];
                    array[j] = temp;
                    index = j;
                }
            }

            k = index;
            if (index == 0) {
                break;
            }
        }
        System.out.println("total:" + sum);
    }


    public static void main(String[] args) {
        //int[] array = {12, 1, 3, 5, 7, 22, 31, 45, 21, 9, 6, 55, 58, 90};
        int[] array = {0, 7, 2, 5, 11, 22, 33, 44, 55, 58, 90};
        sort(array);
        for (int i : array) {
            System.out.println(i);
        }

    }

}
