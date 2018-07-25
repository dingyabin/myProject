package linklist;

/**
 * Created by MrDing
 * Date: 2018/7/1.
 * Time:20:48
 */
public class Main {

    public static void main(String[] args) {
        DummylinkedList<Integer> list = new DummylinkedList<>();
        for (int i = 0; i < 5; i++) {
          list.addFirst(i);
        }
        System.out.println(list);
        list.set(2,55);
        System.out.println(list);
        list.delete(2);
        System.out.println(list);

        list.deleteFirst();
        System.out.println(list);


    }


}
