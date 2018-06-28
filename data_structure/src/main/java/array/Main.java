package array;

/**
 * Created by MrDing
 * Date: 2018/6/29.
 * Time:0:17
 * @author MrDing
 */
public class Main {

    public static void main(String[] args) {
        Array  array=new Array(5);
        for (int i = 0; i < 10; i++) {
            array.add(i,i);
        }
        System.out.println(array.get(3));
        array.addFirst(8);
        array.addLast(9);
        array.add(3,66);
        array.add(4,66);
        array.add(5,66);
        array.set(1,33);

        System.out.println(array);
//        array.deleteAllElement(66);
//        System.out.println(array);
    }
}
