package Java.BagQueueStack;

// Simple Stack Implementation Client.


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

public class Reverse {
    public static void main(String[] args) throws IOException {
        Stack<Integer> stack = new Stack<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Iterator<String> inputIterator = br.lines().iterator();
        try {
            while(inputIterator.hasNext()) {
                int num = Integer.parseInt(inputIterator.next());
                System.out.println("Integer " + num + " will be added to the stack.");
                stack.push(num);
            }
        } catch (NullPointerException | NumberFormatException exception) {
            exception.getStackTrace();
        }
        br.close();

        // Check whether values are successfully added to the Stack.
        System.out.println(stack.size());

        // Pop values from the stack. (Should be reversed order.)
        while (!stack.isEmpty()) {
            System.out.println(stack.pop());
        }
    }
}
