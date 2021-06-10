package Problems;

// For this exercise, java's libraries have been used in order to construct Queue and Stack.
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
    A more complete and consistent set of LIFO stack operations is provided by the Deque interface and its
    implementations, which should be used in preference to this class.

    Example of Object Declaration:
    Deque<E> stack = new ArrayDeque<E>();
 */

/**
 * Exercise 1.3.9.
 */
public class InputHandling {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        System.out.println(exercise1_3_9(args[0]));
        long end = System.currentTimeMillis();
        System.out.println("\nTASK COMPLETED in " + (end - start) / 1000.0 + "s");
    }

    public static String exercise1_3_9(String str) {
        Stack<String> stack = new Stack<>();
        String[] input = str.split(" ");

        for (String s : input) {
            //System.out.println("String from input: " + s);
            if (s.equals(")")) {
                try {
                    String secondOperand = stack.pop();
                    String operator = stack.pop();
                    String firstOperand = stack.pop();
                    String result = String.join(" ",
                            new String[]{"(", firstOperand, operator, secondOperand, s});
                    // System.out.println(result);
                    stack.push(result);
                } catch (NoSuchElementException nse) {
                    System.out.println(Arrays.toString(nse.getStackTrace()));
                }
            } else {
                stack.push(s);
            }
        }
        // String[] test = stack.toArray(new String[0]);
        return String.join(" ", stack.toArray(new String[0]));
    }
}
