package Problems;

// Exercise 1.3.4
/*
    Similar to Dijkstra's Two Stack Algorithm Implementation
 */
import Java.BagQueueStack.Stack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Parentheses {
    public static void main(String[] args) throws IOException{
        Stack<ParenthesesType> stack = new Stack<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] inputString = br.readLine().split("");
        boolean result = false;

        for (String s: inputString) {
            //System.out.println(s);
            if (s.equals("[")) stack.push(ParenthesesType.BRACKET);
            else if (s.equals("{")) stack.push(ParenthesesType.CURLY);
            else if (s.equals("(")) stack.push(ParenthesesType.ROUND);
            else {
                //System.out.println("Current Stack Size: " + stack.size());
                ParenthesesType previousNodeItem = stack.pop();
                //System.out.println("Popped Item: " + previousNodeItem);
                if (s.equals("]")) {
                    if (previousNodeItem == ParenthesesType.BRACKET) result = true;
                } else if (s.equals("}")) {
                    if (previousNodeItem == ParenthesesType.CURLY) result = true;
                } else if (s.equals(")")) {
                    if (previousNodeItem == ParenthesesType.ROUND) result = true;
                }
            }
        }
        System.out.println("Result: " + result);
    }
}

enum ParenthesesType {BRACKET, CURLY, ROUND}
