package Java.BagQueueStack;

/*
    Deque: A double-ended queue or deque (pronounced "deck") is like a stack or a queue but supports adding and
    removing items at both ends.
 */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int size;
    private Node<Item> end;
    private Node<Item> start;

    private class Node<Item> {
        private Item item;
        private Node<Item> previous;
        private Node<Item> next;
    }

    public Deque() {
        // Each node fields will be initialized to null value.
        this.size = 0;
    }

    /**
     * Add an item to the left end. (Do equal operation like enqueue in queue.)
     * @param item
     */
    public void pushLeft(Item item) {
        if (size == 0) {
            start = new Node<>();
            start.item = item;
            end = start;
        } else {
            Node<Item> temp = start;
            start = new Node<>();
            start.item = item;
            start.next = temp;
            temp.previous = start;
        }
        size++;
    }

    /**
     * Add an item to the right end. (Do equal operation like push in Stack.)
     * @param item
     */
    public void pushRight(Item item) {
        if (size == 0) {
            end = new Node<>();
            end.item = item;
            start = end;
        } else {
            Node<Item> temp = end;
            end = new Node<>();
            end.item = item;
            end.previous = temp;
            temp.next = end;
        }
        size++;
    }

    /**
     * Remove and retrieve item from the left end. (Do equal operation like dequeue in queue.)
     * @return item at the very left end.
     * @exception NoSuchElementException if target element is null.
     * @exception NullPointerException if current Deque size is 0
     */
    public Item popLeft() {
        if (size == 0) throw new NullPointerException("Deque is currently empty.");
        else {
            if (start == null) throw new NoSuchElementException("Element Not Found.");
            Node<Item> temp = start;
            start = start.next;
            start.previous = null;  // Prevent Loitering.
            size--;
            return temp.item;
        }
    }

    /**
     * Remove and retrieve item from the right end. (Do equal operation like pop in stack.)
     * @return item at the very right end.
     * @exception NoSuchElementException if target element is null.
     * @exception NullPointerException if current Deque size is 0
     */
    public Item popRight() {
       if (size == 0) throw new NullPointerException("Deque is currently empty.");
       else {
           if (end == null) throw new NoSuchElementException("Element Not Found.");
           Node<Item> temp = end;
           end = end.previous;
           end.next = null; // Prevent Loitering.
           size--;
           return temp.item;
       }
    }

    /**
     * Examine Current Deque Status. (Is the Deque Empty?)
     * @return {@code true} if Deque is currently empty, {@code false} if Deque has an element.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Total number of elements currently on Deque.
     * @return number of elements.
     */
    public int size() {
        return size;
    }

    /**
     * Return Deque Iterator in order to support for-each statement.
     * @return new Deque Iterator that iterates elements on Deque from left end to right end.
     */
    @Override
    public Iterator<Item> iterator() {

        class DequeIterator implements Iterator<Item> {
            Node<Item> current = Deque.this.start;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Item next() {
                if (current == null) throw new NoSuchElementException("Element Not Found.");
                Node<Item> temp = current;
                current = current.next;
                return temp.item;
            }

            /**
             * Remove operation is not supported.
             * @exception UnsupportedOperationException if this method is invoked.
             */
            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        }
        return new DequeIterator();
    }

    /**
     * Test Client
     * @param args command set. (PUR, PUL, POR, POL)
     */
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Deque<Integer> deque = new Deque<>();
        int testNum = 0;

        while (testNum < args.length) {
            System.out.println("***************");
            System.out.println("Num: " + testNum);
            switch (args[testNum].toUpperCase()) {
                case "PUR" -> deque.pushRight(testNum);
                case "PUL" -> deque.pushLeft(testNum);
                case "POR" -> deque.popRight();
                case "POL" -> deque.popLeft();
            }
            System.out.println("Current Deque Status: ");
            System.out.println("START: " + deque.start.item);
            System.out.println("END: " + deque.end.item);
            System.out.println("Size: " + deque.size());
            System.out.println("Is Empty?: " + deque.isEmpty());
            System.out.println("Deque: ");
            for (Integer i: deque) {
                System.out.print(i + " ");
            }
            System.out.println("\n***************\n");
            testNum++;
        }
        long end = System.currentTimeMillis();
        System.out.println("\n\nTASK COMPLETED in " + (end - start) / 1000.0 + "s");
    }
}
