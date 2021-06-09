package Problems;

// Exercise 1.3.6

import Java.BagQueueStack.Stack;
import Java.BagQueueStack.Queue;

import java.util.Iterator;

public class QueueStackExercise {
    public static void main(String[] args) {
        Queue<String> queue = new Queue<>();
        Stack<String> stack = new Stack<>();

        for (int i = 0; i < 10; i++) {
            queue.enqueue(Integer.toString(i));
            // In FIFO order, dequeuing output should be 0,1,2,3,4,5,6,7,8,9
        }

        while (!queue.isEmpty()) {
            stack.push(queue.dequeue());
        }
        System.out.println(stack.peek());   // Should print 9
        System.out.println("Current Stack size: " + stack.size());
        System.out.println("Current Queue Size: " + queue.size());
        while (!stack.isEmpty()) {
            queue.enqueue(stack.pop());
        }
        System.out.println("Current Stack Size: " + stack.size());
        System.out.println("Current Queue Size: " + queue.size());

        while(!queue.isEmpty()) {
            System.out.print(queue.dequeue());
        }
        System.out.println();

    }
}
