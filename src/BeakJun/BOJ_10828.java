package BeakJun;

// BOJ_10828
/*
    Java's Stack and LinkedList library will be used.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

public class BOJ_10828 {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        Stack<Integer> stack = new Stack<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int commandCount = Integer.parseInt(br.readLine());

        while (commandCount > 0) {
            String[] command = br.readLine().split(" ");
            switch (command[0].toLowerCase()) {
                case "push": {
                    stack.push(Integer.parseInt(command[1]));
                    break;
                }
                case "pop": {
                    Integer result = stack.pop();
                    if (result == null) System.out.println(-1);
                    else System.out.println(result);
                    break;
                } case "size": {
                    System.out.println(stack.size());
                    break;
                }
                case "empty": {
                    if (stack.isEmpty()) System.out.println(1);
                    else System.out.println(0);
                    break;
                }
                case "top": {
                    Integer result = stack.peek();
                    if (result == null) System.out.println(-1);
                    else System.out.println(result);
                    break;
                }
            }
            commandCount--;
        }
        long end = System.currentTimeMillis();
        System.out.println("TASK COMPLETED in " + (end - start) / 1000.0 + "s");
    }
}

class Stack<Item> implements Iterable<Item> {
    private int size;
    private Node<Item> current;

    private class Node<Item> {
        private Item item;
        private Node<Item> next;
    }

    public Stack() {
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
