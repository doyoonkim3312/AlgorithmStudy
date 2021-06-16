package Problems;

// Problem 1.3.44 - Text Editor Buffer.
// Q. Develop a data type for a buffer in a text editor.

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

// Deque will be implemented.
public class BufferDeque<Item> implements Iterable<Item> {
    private int size;
    private Node<Item> start;
    private Node<Item> cursor;

    private class Node<Item> {
        private Item item;
        private Node<Item> previous;
        private Node<Item> next;
    }

    public BufferDeque() {
        this.size = 0;
    }

    public void insert(Item item) {
        if (cursor == null) {
            cursor = new Node<>();
            cursor.item = item;
            start = cursor;
            size++;
        } else {
            cursor.item = item;
        }
    }

    public Item get() {
        if (size == 0) throw new NoSuchElementException();
        else {
            if (cursor.item == null) return null;
            else return cursor.item;
        }
    }

    public Item delete() {
        if (size == 0) throw new NullPointerException();
        else {
            Node<Item> temp = cursor;
            cursor = new Node<>();
            cursor.previous = temp.previous;
            cursor.next = temp.next;
            size--;
            return temp.item;
        }
    }

    public void left(int k) {
        if (cursor == null) {
            cursor = new Node<>();
        }
        while (k > 0) {
            if (cursor.previous == null) {
                Node<Item> temp = cursor;
                cursor = new Node<>();
                cursor.next = temp;
                temp.previous = cursor;
                size++;
            } else {
                cursor = cursor.previous;
            }
            k--;
        }
        start = cursor;
    }

    public void right(int k) {
        if (cursor == null) {
            cursor = new Node<>();
            start = cursor;
        }
        while (k > 0) {
            if (cursor.next == null) {
                Node<Item> temp = cursor;
                cursor = new Node<>();
                cursor.previous = temp;
                temp.next = cursor;
                size++;
            } else {
                cursor = cursor.previous;
            }
            k--;
        }
    }

    public int size() {
        return size;
    }

    public String flush() {
        StringBuilder sb = new StringBuilder();
        Node<Item> current = start;
        while (true) {
            if (current.item == null && current.next == null) break;
            else {
                if (current.next == null) {
                    sb.append(current.item);
                    break;
                } else {
                    sb.append(current.item).append(" ");
                    current = current.next;
                }
            }
        }
        return sb.toString();

    }

    public void getCursorInformation() {
        try {
            if (cursor == null) {
                System.out.println("Cursor Not found");
            } else {
                System.out.println("CURSOR INFORMATION");
                System.out.println("Item: " + cursor.item);
                if (cursor.next.item == null) System.out.println("NULL");
                else System.out.println("NEXT Node: " + cursor.next.item);
                if (cursor.previous.item == null) System.out.println("NULL");
                else System.out.println("Previous Node: " + cursor.previous.item);
            }
        } catch (NullPointerException npe) {
            System.out.println("One of the related Nodes are null");
        }
    }

    @Override
    public Iterator<Item> iterator() {

        class BufferIterator implements Iterator<Item> {
            Node<Item> current = start;

            @Override
            public boolean hasNext() {
                return current == null;
            }

            @Override
            public Item next() {
                if (current == null) throw new NoSuchElementException();
                Node<Item> temp = current;
                current = current.next;
                if (current.item == null) return null;
                return temp.item;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        }
        return new BufferIterator();
    }

    // Test Client
    public static void main(String[] args) {
        BufferDeque<Character> textBuffer = new BufferDeque<>();

        for (int i = 0; i < args.length; i++) {
            System.out.println("********************");
            System.out.println("Char: " + i);
            textBuffer.getCursorInformation();
            switch (args[i].toUpperCase()) {
                case "INSERT" -> textBuffer.insert(args[++i].charAt(0));
                case "GET" -> System.out.println("GET Result: " + textBuffer.get());
                case "DELETE" -> System.out.println("Delete Completed: " + textBuffer.delete());
                case "LEFT" -> {
                    try {
                        int k = Integer.parseInt(args[++i]);
                        textBuffer.left(k);
                        System.out.println("Cursor Moved " + k + " position to the left.");
                    } catch (Exception e) {
                        System.out.println(Arrays.toString(e.getStackTrace()));
                    }
                }
                case "RIGHT" -> {
                    try {
                        int k = Integer.parseInt(args[++i]);
                        textBuffer.right(k);
                        System.out.println("Cursor Moved " + k + " position to the right.");
                    } catch (Exception e) {
                        System.out.println(Arrays.toString(e.getStackTrace()));
                    }
                }
            }
            System.out.println("SIZE: " + textBuffer.size());
            System.out.println("BUFFER: ");
            for (Character c: textBuffer) {
                if (c == null) System.out.print("null");
                else System.out.print(c + " ");
            }
            System.out.println("\n********************\n");
        }

        System.out.println(textBuffer.flush());
    }
}
