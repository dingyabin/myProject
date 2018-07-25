package sort;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by MrDing
 * Date: 2018/7/16.
 * Time:22:16
 */
public class BubbleSort {


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int[] array = {7, 1, 3, 34, 67, 232, 78, 12, 56};
        sort(array);
        System.out.println(Arrays.toString(array));


        FutureTask<String> stringFutureTask = new FutureTask<>(() -> {
            Thread.sleep(2000);
            return new Random().nextInt() + "";
        });

        stringFutureTask.run();
        System.out.println("-----------------"+stringFutureTask.get());
        System.out.println("-----------------"+stringFutureTask.get());
        System.out.println("-----------------"+stringFutureTask.get());


    }


    public static void sort(int[] array) {
        int okIndex = array.length - 1;
        for (int i = 0; i < array.length; i++) {
            boolean ok = true;
            int last = 0;
            for (int j = 0; j < okIndex; j++) {
                if (array[j] > array[j + 1]) {
                    int tmp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = tmp;
                    ok = false;
                    last = j;
                }
            }
            okIndex = last;
            if (ok) {
                break;
            }
        }
    }


}





