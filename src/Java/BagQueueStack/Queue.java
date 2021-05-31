package Java.BagQueueStack;

import java.io.*;
import java.util.Iterator;
import java.util.Scanner;

// Simple FIFO Queue Implementation.

public class Queue<Item> implements Iterable<Item> {
    private int size;
    private Node<Item> head; // Least Recently added node.
    private Node<Item> current; // Most Recently added node.

    private class Node<Item> {
        private Item data;
        private Node<Item> pointer;
    }

    public Queue() {
        this.size = 0;
        this.head = null;
        this.current = null;
    }

    /**
     * Add new item to the queue.
     * @param data
     */
    public void enqueue(Item data) {
        if (head == null) {
            current = new Node<Item>();
            current.data = data;
            head = current;
        } else {
            Node<Item> temp = current;
            current = new Node<Item>();
            current.data = data;
            temp.pointer = current;
        }
        size++;
    }

    /**
     * return the least recently added item before removed from queue.
     * NOTE: dequeue is not the one that related to iterator object.
     * @return Least recently added item in current queue situation.
     */
    public Item dequeue() {
        if (head == null) throw new NullPointerException();
        Item temp = head.data;
        head = head.pointer;
        size--;
        return temp;
    }

    /**
     * Examine queue status. (Is Queue empty?)
     * @return {@code true} if queue is empty, {@code false} if queue isn't empty.
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Return the number of items on queue.
     * @return size of queue.
     */
    public int size() {
        return size;
    }

    /**
     * Return string representation, which is a sequence of elements on queue in FIFO order.
     * @return string representation of queue.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Item item : this) {
            sb.append(item).append(" ");
        }
        return sb.toString();
    }

    /**
     * Return Iterator object.
     * @return
     */
    @Override
    public Iterator<Item> iterator() {
        // Supporting Iteration is not requisite.
        class LinkedQueue implements Iterator<Item>{
            Node<Item> holder;

            public LinkedQueue(Node<Item> head) {
                this.holder = head;
            }

            @Override
            public boolean hasNext() {
                return holder != null;
            }

            @Override
            public Item next() {
                if (!hasNext()) throw new NullPointerException();
                Node<Item> temp = holder;
                holder = holder.pointer;
                return temp.data;
            }
        }
        return new LinkedQueue(head);
    }

    // Test Client Example
    public static void main(String[] args) throws FileNotFoundException {
        Queue<String> queue = new Queue<>();
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String temp = sc.next();
            if (!temp.equals("-")) {
                queue.enqueue(temp);
            } else {
                if (!queue.isEmpty()) {
                    System.out.print(queue.dequeue() + " ");
                }
            }
        }
        System.out.println("(" + queue.size() + " left on queue)");

        System.out.println("Element left on queue");
        for (String s: queue) {
            System.out.println(s);
        }
    }
}
