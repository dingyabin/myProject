package net.dingyabin;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by MrDing
 * Date: 2020/5/28.
 * Time:14:54
 */
public  class Test {


    /**
     * 字符串数字求和
     *
     * @param s1
     * @param s2
     * @return
     */
    private static String strAdd(String s1, String s2) {
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
            if (tmp > max) {
                max = tmp;
            }
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
            if (curSum > 0) {
                curSum = curSum + array[i];
            } else {
                curSum = array[i];
            }

            if (curSum > maxSum) {
                maxSum = curSum;
            }
        }
        return maxSum;
    }


    /**
     * 连续最大子数组和 o(n)
     * @param array
     * @return
     */
    public static  int finMax3(int[] array) {
        //max就是上面的dp[i]
        int curMax = array[0];
        //因为这个dp[i]老是变，所以比如你dp[4]是8 dp[5]就变成-7了，所以需要res保存一下
        int res = array[0];
        for (int i = 1; i < array.length; i++) {
            curMax = Math.max(curMax + array[i], array[i]);
            res = Math.max(res, curMax);
        }
        return res;
    }


    /**
     * 压缩字符串
     * @param args
     */

    public static String zipStr(String args){
        char last= args.charAt(0);
        int count = 1;
        String result = "";
        for (int i = 1; i <args.length() ; i++) {
            char current = args.charAt(i);
            if(last == current){
                count++;
            }else {
                result = result + last + (count > 1 ? count : "");
                last = current;
                count = 1;
            }
        }
        return result + last + (count > 1 ? count : "");
    }


    /**
     * 快拍
     * @param array
     * @param start
     * @param end
     */
    public static void quitSort(int[] array, int start, int end) {
        if (start>=end){
            return;
        }
        int low = start;
        int high = end;
        int tmp = array[low];
        while (low < high) {
            while (low < high && tmp <= array[high]) {
                high--;
            }
            array[low] = array[high];
            while (low < high && array[low] <= tmp) {
                low++;
            }
            array[high] = array[low];
        }
        array[low] = tmp;
        quitSort(array, start, low-1);
        quitSort(array, low + 1, end);
    }


    /**
     * 最长无重复连续字符串
     * @param str
     * @return
     */
    public static  int longestNoRepeatStr(String str){
        Set<Character> set = new HashSet<>();
        int max = 1;
        for (int i = 0,j =0; i < str.length(); i++) {
            if (!set.contains(str.charAt(i))) {
                set.add(str.charAt(i));
                max = Math.max(max,set.size() );
            }else{
                while (set.contains(str.charAt(i))){
                    set.remove(str.charAt(j));
                    j++;
                }
                set.add(str.charAt(i));
            }
        }
        return max;
    }




    private  static  class  MaxHuiWenString{

        private static  int start = 0;

        private static  int max = 1;

        /**
         * 最大回文串
         *
         * @return
         */
        public static String maxHuiWenString(String str) {
            if (str ==  null || str.length() == 0) {
                return "";
            }
            for (int i = 0; i < str.length(); i++) {
                findHuiWen(str, i - 1, i + 1);
                findHuiWen(str, i, i + 1);
            }
            return str.substring(start, start + max);
        }


        private static void findHuiWen(String str, int left, int right) {
            while (left >= 0 && right < str.length() && str.charAt(left) == str.charAt(right)) {
                if (right - left + 1 > max) {
                    start = left;
                    max = right - left + 1;
                }
                left--;
                right++;
            }
        }
    }







    //链表双双翻转

//    public ListNode reverseDoubleList(ListNode head) {
//        if (head == null || head.next == null)
//            return head;
//
//        ListNode p = head;
//        // 保存返回的链表头
//        ListNode res = p.next;
//
//        ListNode pre = null;
//        ListNode pnextnext = null;
//
//        while (p != null && p.next != null) {
//            pnextnext = p.next.next;
//            p.next.next = p;
//
//            // 注意判断是否是第一次，第一次的pre不会自动向前走
//            if (pre == null) {
//                pre = p;
//            } else {
//                pre.next = p.next;
//            }
//            p.next = pnextnext;
//
//            pre = p;
//            p = p.next;
//
//        }
//        return res;
//    }



    //三数之和=0
//       private static Set<List<Integer>> fing_sum3(List<Integer> arr){
//                 Collections.sort(arr);//先对数组进行排序
//                 for(Integer aInteger : arr){
//                         System.out.println(aInteger);
//                     }
//                 List<Integer> list3 ;
//                 Set<List<Integer>> setList = new HashSet<>();
//
//                 for(int i = 0; i < arr.size()-2; i++){
//                         int j = i+1;
//                         int k = arr.size() - 1;
//                         while(j < k){
//                                 //先固定arr[i]不动，左右一定逼近;arr[j]太小，往前移动一位
//                                 if (arr.get(i) + arr.get(j) + arr.get(k) < 0 ) {
//                                  j++;
//                                } else if(arr.get(i) + arr.get(j) + arr.get(k) > 0){
//                                    k--;
//                                }else {
//                                    list3 = new ArrayList<>();
//                                    list3.add(arr.get(i));
//                                    list3.add(arr.get(j));
//                                    list3.add(arr.get(k));
//                                    setList.add(list3);
//                                   // j++;//这个需要删掉，不然的话有些元素没有被计算
//                                    k--;
//                                }
//                        }
//                     }
//                 return setList;
//             }
//




    public void groupSameWords(String[] strs){

        Map<String,List<String>> map = new HashMap<>();

        for (String str : strs) {

            int[] arr= new int[26];
            for (int i = 0; i < str.length(); i++) {
                  int index = str.charAt(i) - 'a';
                  arr[index] = arr[index]+1;
            }
            String key = "";
            for (int i : arr) {
                key += i;
            }
            if (map.containsKey(key)) {
                map.get(key).add(str);
            } else {
                map.put(key, new ArrayList<>());
            }
        }
    }







    public static void move(int[] arry ) {
        int sum = 0;
        for (int num : arry) {
            sum = sum + num;
        }
        int per = sum / arry.length;

        int[] moreArray = new int[arry.length];
        int[] lessArray = new int[arry.length];

        for (int i = 0; i < arry.length; i++) {
            if (arry[i] < per) {
                lessArray[i] = arry[i];
            } else if (arry[i] > per) {
                moreArray[i] = arry[i];
            }
        }

        int n = 0;
        for (int i = 0; i < lessArray.length; i++) {
            while (moreArray[n] ==0) {
                n++;
            }
            while (moreArray[n] > per && lessArray[i] > 0 && lessArray[i] < per) {
                moreArray[n] = moreArray[n] - 1;
                lessArray[i] = lessArray[i] + 1;

                arry[n] = arry[n] - 1;
                arry[i] = arry[i] + 1;

                System.out.println(n + "-->" + i);
                while (moreArray[n] == per || moreArray[n] == 0) {
                    n++;
                }
            }

        }

        for (int i = 0; i < arry.length; i++) {
            System.out.println(arry[i]+"--");
        }

    }










    public static void main(String[] args) {

        // System.out.println(max("1234567","323434789" ));

//        System.out.println(finMax(new int[]{1, 6, -4, 5, -3}));
//        System.out.println(finMax2(new int[]{1, 6, -4, 5, -3}));
//        System.out.println(finMax3(new int[]{1, 6, -4, 5, -3}));
//        System.out.println(zipStr("aaaabcddeggh"));
//        int[] ints = {49, 38, 65, 97, 23, 22, 76, 1, 5, 8, 2, 0, -1, 22};
//        quitSort(ints,0 , ints.length-1);
//        System.out.println(Arrays.toString(ints));
//        System.out.println(longestNoRepeatStr("abcabcbb"));
//        System.out.println(MaxHuiWenString.maxHuiWenString("abcdffdh"));
        move(new int[]{8, 4, 3, 1, 22});
    }




}
