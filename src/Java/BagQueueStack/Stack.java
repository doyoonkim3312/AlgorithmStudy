package Java.BagQueueStack;

// Simple Pushdown stack (typical stack) implementation.

import java.io.BufferedReader;
import java.util.Iterator;
import java.util.Scanner;

public class Stack<Item> implements Iterable<Item> {
    private int size;
    private Node<Item> current;

    private class Node<Item> {
        private Item data;
        private Node<Item> pointer;
    }

    /**
     * Default no-args constructor for initializing Stack for use.
     */
    public Stack() {
        this.size = 0;
    }

    /**
     * Add an item to Stack.
     * @param data
     */
    public void push(Item data) {
        if (current == null) {
            current = new Node<Item>();
            current.data = data;
        } else {
            Node<Item> temp = current;
            current = new Node<Item>();
            current.data = data;
            current.pointer = temp;
        }
        size++;
    }

    /**
     * Remove and return most recently added item. (Return and remove item from stack based on LIFO policy.)
     * @return item on very top of the current stack.
     */
    public Item pop() {
        if (current == null) throw new NullPointerException();
        Node<Item> temp = current;
        current = current.pointer;
        size--;
        return temp.data;
    }

    /**
     * Examine current stack status. (whether stack is empty or not.)
     * @return {@code true} if stack is empty, {@code false} if stack is not empty.
     */
    public boolean isEmpty() {
        return this.size() != 0;
    }

    /**
     * Return total number of items in Stack.
     * @return current size of stack.
     */
    public int size() {
        return this.size;
    }

    @Override
    public Iterator<Item> iterator() {
        return null;
    }

    // Test Client Code. - Dijkastra's Two-Stack Algorithm for Expression Evaluation.)
    public static void main(String[] args) {
        Stack<String> operator = new Stack<>();
        Stack<Double> operand = new Stack<>();

        Scanner sc = new Scanner(System.in);
        String[] expression = sc.nextLine().split(" ");

        for (String s: expression) {
            if (s.equals("(")); // Ignore Left-parentheses
            else if (s.equals("+")) operator.push(s);
            else if (s.equals("-")) operator.push(s);
            else if (s.equals("*")) operator.push(s);
            else if (s.equals("/")) operator.push(s);
            else if (s.equals("sqrt")) operator.push(s);
            else if (s.equals(")")) {
                double value = operand.pop();
                String currentOperator = operator.pop();
                if (currentOperator.equals("+")) value = operand.pop() + value;
                else if (currentOperator.equals("-")) value = operand.pop() - value;
                else if (currentOperator.equals("*")) value = operand.pop() * value;
                else if (currentOperator.equals("/")) value = operand.pop() / value;
                else if (currentOperator.equals("sqrt")) value = Math.sqrt(value);
                operand.push(value);
            }
            else {
                try {
                    double temp = Double.parseDouble(s);
                    operand.push(temp);
                } catch (Exception e) {
                    System.out.println("String value cannot be converted into Double type: " + s);
                }
            }
        }
        System.out.println("Result: " + operand.pop());
    }
}
