package stack;

/**
 * Created by MrDing
 * Date: 2018/6/30.
 * Time:20:08
 */
public class Main {

    public static void main(String[] args) {

        String sss="{[()]}";
        Stack<Character> stack = new ArrayStack<>();
        for (int i = 0; i < sss.length(); i++) {
            char c = sss.charAt(i);
            if (c=='['||c=='{'||c=='('){
                stack.push(c);
            }else {
                if (stack.isEmpty()){
                    System.out.println("非法");
                    return;
                }
                char pop = stack.pop();
                if (c == ']' && pop != '[') {
                    System.out.println("非法");
                    return;
                }
                if (c == '}' && pop != '{') {
                    System.out.println("非法");
                    return;
                }
                if (c == ')' && pop != '(') {
                    System.out.println("非法");
                    return;
                }
            }
        }
        if (!stack.isEmpty()) {
            System.out.println("非法");
            return;
        }
        System.out.println("ok!");
    }


}
