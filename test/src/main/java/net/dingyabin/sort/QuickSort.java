package net.dingyabin.sort;

import java.util.Arrays;

/**
 * Created by MrDing
 * Date: 2018/8/8.
 * Time:0:45
 */
public class QuickSort {


    public static void main(String[] args) {
        int a[]={1,5,8,33,2,22,77,44};
        sort(a, 0, a.length-1);
        System.out.println(Arrays.toString(a));
    }


    public static  void sort(int[] array, int start, int end) {
        if (end <= start) {
            return;
        }
        int move = move(array, start, end);
        sort(array, start, move - 1);
        sort(array, move + 1, end);
    }


    public static  int move(int[] array, int start, int end) {
        int left = start;
        int right = end;
        int index = start;
        int base = array[start];

        while (left <= right) {
            while (left <= right) {
                if (array[right] < base) {
                    array[left] = array[right];
                    index = right;
                    left++;
                    break;
                }
                right--;
            }

            while (left <= right) {
                if (array[left] > base) {
                    array[right]=array[left];
                    index=left;
                    right-- ;
                    break;
                }
                left++;
            }
        }

        array[index] = base;
        return index;
    }

}
