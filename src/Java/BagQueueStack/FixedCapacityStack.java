package Java.BagQueueStack;

// Fixed Capacity LIFO Stack Implementation with resizing method.

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class FixedCapacityStack<Item> implements Iterable<Item> {
    private int stackSize;
    private int index;
    private Item[] arr;

    /**
     * Fixed Capacity Stack constructor that accept size of stack as an argument.
     * @param stackSize size of stack.
     */
    public FixedCapacityStack (int stackSize) {
        this.stackSize = stackSize;
        this.index = 0;
        // Caution: Generic Array Creation is disallowed in Java; The error occurred by this type casting can be ignored.
        arr = (Item[]) new Object[this.stackSize];
    }

    /**
     * Add new item to stack. If stack is full when client tries to add new item to the stack, stack will be doubled its
     * capacity, and new item will be added on stack.
     * @param item
     * @exception StackOverflowError can be thrown, if the index exceeds assigned size of stack.
     */
    public void push(Item item) {
        if (index > stackSize) throw new StackOverflowError();
        if (index == arr.length) resize(2 * arr.length);
        arr[index++] = item;
    }

    /**
     * Remove and return item on Stack based on LIFO policy.
     * @return the most recently added item.
     * @exception StackOverflowError if index is smaller than 0.
     */
    public Item pop() {
        if (index <= 0) throw new StackOverflowError();
        return arr[--index];
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
        return index;
    }

    /**
     * Return current capacity of stack.
     * @return current capacity of stack.
     */
    public int capacity() {
        return stackSize - index;
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

        class FixedCapacityStackIterator<Item> implements Iterator<Item> {
            private final Item[] arr;
            private int index;

            public FixedCapacityStackIterator(Item[] arr) {
                this.arr = arr;
                this.index = 0;
            }

            @Override
            public boolean hasNext() {
                if (index >= arr.length) throw new IndexOutOfBoundsException();
                if (arr[index] != null) return true;
                return false;
            }

            @Override
            public Item next() {
                if (!hasNext()) throw new NoSuchElementException();
                return arr[index++];
            }
        }
        return new FixedCapacityStackIterator<>(this.arr);
    }

    // Test Client
    public static void main(String[] args) throws IOException {
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
