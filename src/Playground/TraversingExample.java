package Playground;

import java.awt.event.ItemEvent;
import java.util.ArrayList;

public class TraversingExample {
    static class Node<Item> {
        private Item item;
        private Node<Item> next;
    }

    public static void main(String[] args) {
        Node<String> first = new Node();
        Node<String> second = new Node();
        Node<String> last = new Node();

        first.item = "to";
        second.item = "be";
        last.item = "or";

        first.next = second;
        second.next = last;

        // Insert item at the last.
        Node<String> oldLast = last;
        last = new Node();
        last.item = "not";
        oldLast.next = last;

        ArrayList<String> stringResult = new ArrayList<>();
        // Traversing.
        // Structure Fundamental: for (from; until; update) {}
        // This idiom is as natural as the standard idiom for iterating through the items in an array.
        for (Node<String> x = first; x != null; x = x.next) {
            stringResult.add(x.item);
        }

        System.out.println(stringResult.toString());
    }
}
