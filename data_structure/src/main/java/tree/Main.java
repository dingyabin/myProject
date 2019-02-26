package tree;

import java.util.Random;

/**
 * Created by MrDing
 * Date: 2018/7/5.
 * Time:0:42
 */
public class Main {


    public static void main(String[] args) {
        BST<Integer> bst = new BST<>();
        Random ran = new Random();
        for (int i = 0; i < 10; i++) {
            int i1 = ran.nextInt(1000);
            System.out.println(i1);
            bst.add2(i1);
        }
        System.out.println("---------");
//        bst.preOrderNR();
//        System.out.println("---------");
//        bst.preOrder();
            bst.inOrder();
//        bst.levelOrder();
 //       System.out.println("*********");
    }
}
