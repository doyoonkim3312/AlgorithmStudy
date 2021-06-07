package Java.BagQueueStack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Fixed Capacity Pushdown (LIFO) Stack Implementation with dynamic sizing implementation.
 * @param <Item> Generic Representation.
 */
public class FixedCapacityStack<Item> implements Iterable<Item> {
    private int elementCount;
    private Item[] arr;

    /**
     * Fixed Capacity Stack constructor that accept size of stack as an argument.
     * @param stackSize size of stack.
     */
    public FixedCapacityStack (int stackSize) {
        this.elementCount = 0;
        // Caution: Generic Array Creation is disallowed in Java; The error occurred by this type casting can be ignored.
        arr = (Item[]) new Object[stackSize];
    }

    /**
     * Add new item to stack. If stack is full when client tries to add new item to the stack, stack will be doubled its
     * capacity, and new item will be added on stack.
     * @param item
     * @exception StackOverflowError can be thrown, if the index exceeds assigned size of stack.
     */
    public void push(Item item) {
        if (elementCount > arr.length) throw new StackOverflowError();
        if (elementCount == arr.length) resize(2 * arr.length);
        arr[elementCount++] = item;
    }

    /**
     * Remove and return item on Stack based on LIFO policy.
     * @return the most recently added item.
     * @exception StackOverflowError if index is smaller than 0.
     */
    public Item pop() {
        if (elementCount <= 0) throw new StackOverflowError();
        Item temp = arr[--elementCount];
        /*
            Loitering: Loitering is a term represents certain condition, which holding a reference to an item that is no
            longer needed (no longer being accessed).
         */
        arr[elementCount] = null;   // Avoid Loitering;
        // Dynamic Stack Resizing: Test Condition: whether the stack size is less than one-fourth the array size.
        if (elementCount > 0 && elementCount == arr.length / 4) resize(arr.length / 2);
        return temp;
    }

    /**
     * Examine current status of Stack. (Whether stack is empty or not)
     * @return {@code true} if stack is empty, {@code false} if stack is not empty.
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Return total number of items on stack.
     * @return total number of times on stack.
     */
    public int size() {
        return elementCount;
    }

    /**
     * Return current capacity of stack.
     * @return current capacity of stack.
     */
    public int capacity() {
        return arr.length - elementCount;
    }

    /**
     * Extend stack size.
     * @param max new stack size (Should be greater than previous stack size.)
     * @exception IllegalArgumentException if new stack size is smaller than current size of stack.
     */
    public void resize(int max) {
        if (max < arr.length) throw new IllegalArgumentException("New stack size should be greater than size of previous" +
                "stack.");
        Item[] temp = (Item[]) new Object[max];
        for (int i = 0; i < arr.length; i++) {
            temp[i] = arr[i];
        }
        arr = temp;
    }

    /**
     * Generate Iterator of this stack.
     * @return new Iterator object of this stack.
     */
    @Override
    public Iterator<Item> iterator() {
        return new FixedCapacityStackIterator();
    }

    // Nested Class for Iterator. Note: Nested class can access the instance variables of the enclosing class.
    private class FixedCapacityStackIterator implements Iterator<Item> {
        // Create Iterator object based on LIFO policy.
        private int index = elementCount - 1;

        @Override
        public boolean hasNext() {
            return index >= 0;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return arr[index--];
        }

        /**
         * Remove operation is not supported, because interleaving iteration with operations that modify the data
         * structure is best avoided.
         * @exception UnsupportedOperationException when this operation is called.
         */
        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove operation is not supported.")   ;
        }
    }

    // Test Client
    public static void main(String[] args) throws IOException {
        testClient2(args);
    }

    public static void testClient2(String[] args) throws IOException {
        FixedCapacityStack<String> testStack = new FixedCapacityStack<>(10);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] inputArr = br.readLine().split(" ");

        for (String s: inputArr) {
            testStack.push(s);
        }

        Iterator<String> stackIterator = testStack.iterator();
        while (stackIterator.hasNext()) {
            System.out.print(stackIterator.next() + " ");
        }
        System.out.println();
        System.out.println("Task Completed.");
    }

    public static void testClient1(String[] args) throws IOException {
        FixedCapacityStack<String> testStack = new FixedCapacityStack<>(50);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] inputArr = br.readLine().split(" ");

        for (String s: inputArr) {
            if (!s.equals("-")) {
                testStack.push(s);
            } else {
                if (!testStack.isEmpty()) {
                    System.out.print(testStack.pop() + " ");
                }
            }
        }

        System.out.println("( " + testStack.size() + " left on stack)");
        System.out.println("Current Stack Capacity: " + testStack.capacity());
    }
}
