package net.dingyabin;

/**
 * Created by MrDing
 * Date: 2020/5/28.
 * Time:14:54
 */
public class Test {


    /**
     * 字符串数字求和
     *
     * @param s1
     * @param s2
     * @return
     */
    private static String max(String s1, String s2) {
        String result = "";

        boolean up = false;
        int index1;
        int index2;
        for (index1 = s1.length() - 1, index2 = s2.length() - 1; index1 >= 0 || index2 >= 0; index1--, index2--) {

            int int1 = index1 >= 0 ? Integer.parseInt(String.valueOf(s1.charAt(index1))) : 0;
            int int2 = index2 >= 0 ? Integer.parseInt(String.valueOf(s2.charAt(index2))) : 0;

            int sum = int1 + int2;

            if (up) {
                result = (1 + sum % 10) + result;
            } else {
                result = sum % 10 + result;
            }
            up = sum >= 10;
        }
        return result;
    }


    /**
     * 连续最大子数组和 o(n^2)
     *
     * @param array
     * @return
     */
    public static int finMax(int[] array) {
        int max = array[0];
        for (int i = 0; i < array.length; i++) {
            int tmp = array[i];
            for (int j = i + 1; j < array.length; j++) {
                tmp = tmp + array[j];
                if (tmp > max) {
                    max = tmp;
                }
            }
        }
        return max;
    }

    /**
     * 连续最大子数组和 o(n)
     *
     * @param array
     * @return
     */
    public static int finMax2(int[] array) {
        int curSum = array[0];
        int maxSum = array[0];

        for (int i = 1; i < array.length; i++) {
            if (curSum < 0) {
                curSum = array[i];
            } else {
                curSum = curSum + array[i];

            }
            if (curSum > maxSum) {
                maxSum = curSum;
            }
        }
        return maxSum;
    }


    public static void main(String[] args) {

        // System.out.println(max("1234567","323434789" ));

        System.out.println(finMax(new int[]{1, -2, 4, -5, -3}));
        System.out.println(finMax2(new int[]{1, -2, 4, -5, -3}));
    }


}
