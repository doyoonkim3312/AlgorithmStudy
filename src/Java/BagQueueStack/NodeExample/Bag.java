package Java.BagQueueStack.NodeExample;

// Example of simple Bag structure using Singly Linked Node.
/*
    Iterable vs. Iterator
    1. Iterable: An Iterable is a simple representation of a series of elements that can be iterated over. (DOESN'T HAVE
    ANY ITERATION STATE)
    2. Iterator: An Iterator is the object with ITERATION STATE.
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Simple Bag Structure Implementation.
 * @param <Item>
 */
public class Bag<Item> implements Iterable<Item> {
    private int size;
    private Node<Item> current;

    @Override
    public Iterator<Item> iterator() {

        class BagIterator<Item> implements Iterator<Item> {
            private Node<Item> current;

            public BagIterator(Node<Item> current) {
                this.current = current;
            }

            @Override
            public boolean hasNext() {
                // Current is the one that updated as soon as next() method is executed.
                return current != null;
            }

            @Override
            public Item next() {
                if (!hasNext()) throw new NoSuchElementException();
                // print only data.
                Item temp = current.data;
                current = current.linkNode;
                return temp;
            }
        }

        return new BagIterator<>(this.current);
    }

    private class Node<Item> {
        private Item data;
        private Node<Item> linkNode;
    }

    /**
     * No-arg constructor, creates empty bag.
     */
    public Bag() {
        this.size = 0;
    }

    /**
     * Add and Item by create new Node and link it to the previous node.
     * @param data
     */
    public void add(Item data) {
        if (current == null) {
            current = new Node<Item>();
            current.data = data;
            size++;
        } else {
            Node<Item> temp = current;
            current = new Node<Item>();
            current.data = data;
            current.linkNode = temp;
            size++;
        }
    }

    /**
     * Check whether the Bag is empty or not.
     * @return {@code true} if bag is empty, {@code false} if bag is not empty.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Return current size of bag.
     * @return size of bag.
     */
    public int size() {
        return size;
    }



    // Unit Test Client Code
    public static void main(String[] args) {
        System.out.println("Simple Bag Structure Implementation");
        Bag<String> testList = new Bag<>();
        System.out.println(testList.isEmpty());
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Iterator<String> inputIterator = br.lines().iterator();
        while (inputIterator.hasNext()) {
            testList.add(inputIterator.next());
        }

        System.out.println("Size: " + testList.size());
        for (String data: testList) {
            System.out.println(data);
        }
    }
}
