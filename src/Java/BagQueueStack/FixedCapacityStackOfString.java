package Java.BagQueueStack;

// Implementation Example of Stack that has a limited capacity.

import java.util.Scanner;

public class FixedCapacityStackOfString {
    private int size;
    private int index;
    private String[] stackEntries;

    /**
     * Default constructor that accepts size of stack entry as an argument.
     * @param size size of stack entries.
     */
    public FixedCapacityStackOfString(int size) {
        this.size = size;
        stackEntries = new String[this.size];
    }

    public void push(String item) {
        /*
            Code below performs same operation as this code:
            stackEntries[index] = item;
            index++;
         */
        stackEntries[index++] = item;
    }

    public String pop() {
        /*
            Code below performs same operation as this code:
            String temp = stackEntries[index];
            index--;
            return temp;
         */
        // System.out.println("Current Index: " + index);
        int indexChanged = --index;
        String temp = stackEntries[indexChanged];
        // System.out.println("Changed Index: " + indexChanged);
        return temp;
    }

    public boolean isEmpty() {
        return index == 0;
    }

    public int size() {
        return index;
    }

    // Test Client
    public static void main(String[] args) {
        FixedCapacityStackOfString testStack = new FixedCapacityStackOfString(100);
        Scanner sc = new Scanner(System.in);
        String[] input = sc.nextLine().split(" ");
        for (String s: input) {
            // System.out.print(s);
            if (!s.equals("-")) {
                testStack.push(s);
            } else if (!testStack.isEmpty()) {
                System.out.print(testStack.pop() + " ");
                //testStack.pop();
            }
        }

        System.out.println(testStack.size() + " left on stack");
    }


}
