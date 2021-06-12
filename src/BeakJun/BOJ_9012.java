package BeakJun;

//BOJ 9012

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Iterator;

public class BOJ_9012 {
    public static void main(String[] args) throws IOException {
        StackParen<String> stack = new StackParen<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int commandCount = Integer.parseInt(br.readLine());

        while (commandCount > 0) {
            String[] command = br.readLine().split("");
            for (String s: command) {
                if (s.equals("(")) stack.push(s);
                else {
                    String target = stack.peek();
                    if (target != null && target.equals("(")) stack.pop();
                    else stack.push(s);
                }
            }
            if (stack.isEmpty()) System.out.println("YES");
            else System.out.println("NO");
            stack = new StackParen<>();
            commandCount--;
        }
    }
}

class StackParen<Item> implements Iterable<Item> {
    private int size;
    private Node<Item> current;

    private class Node<Item> {
        private Item item;
        private Node<Item> next;
    }

    public StackParen() {
        this.size = 0;
        this.current = null;
    }

    public void push(Item item) {
        if (current == null) {
            current = new Node<>();
            current.item = item;
        } else {
            Node<Item> temp = current;
            current = new Node<>();
            current.item = item;
            current.next = temp;
        }
        size++;
    }

    public Item pop() {
        if (current == null) return null;
        Node<Item> temp = current;
        current = temp.next;
        size--;
        return temp.item;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public Item peek() {
        if (current == null) return null;
        return current.item;
    }

    @Override
    public Iterator<Item> iterator() {
        return null;
    }
}

