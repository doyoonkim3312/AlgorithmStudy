package Kotlin.BagQueueStack

import java.lang.NullPointerException
import java.util.Scanner

// Simple FIFO Queue Implementation (Kotlin)

public class Queue<Item>: Iterable<Item> {
    private var size: Int = 0
    private var current: Node<Item>? = null
    private var head: Node<Item>? = null

    private class Node<Item>(var data: Item, var pointer: Node<Item>?) {
    }

    fun enqueue(data: Item): Unit {
        if (size == 0) {
            // zero-size value means this queue is currently empty.
            current = Node<Item>(data, null)
            head = current
            size++
        } else {
            var temp: Node<Item>? = current
            current = Node<Item>(data, null)
            temp?.pointer = current
            size++
        }
    }

    fun dequeue(): Item? {
        if (head == null) throw NoSuchElementException()
        var temp: Node<Item>? = head
        // ?.: Safe Call operator, which returns certain value if property is not null, returns null without NPE if
        // property is null.
        head = head?.pointer
        size--
        return temp?.data
    }

    /**
     * Examine whether queue is currently empty or not.
     * @return true, if queue is empty, false, if queue is not empty.
     */
    fun isEmpty(): Boolean {
        return size() == 0;
    }

    fun size(): Int {
        return size
    }


    override fun iterator(): Iterator<Item> {

        class LinkedIterator(head: Node<Item>?): Iterator<Item> {

            /**
             * Check whether iterator has a next value.
             * @return true, if iterator has next value to access, false, if iterator has no more value to access.
             */
            override fun hasNext(): Boolean {
                return head?.pointer != null
            }

            /**
             * Return next value in this iterator.
             * @return Next Item type value in this iterator.
             * @exception NullPointerException will be thrown, if iterator has no more value to access, or, if function
             * returns null value.
             */
            override fun next(): Item {
                if (!hasNext()) throw NullPointerException()
                var temp: Node<Item>? = head
                head = head?.pointer
                return temp!!.data  // If there is no Item value to return, it will throw NullPointerException.
            }

        }
        return LinkedIterator(head)
    }

}

// Test Client code for unit test.
fun main(args: Array<String>) {
    val queue: Queue<Int> = Queue<Int>()
    val sc = Scanner(System.`in`)
    val inputIterator: Iterator<String> = sc.nextLine().split(" ").iterator()
    while (inputIterator.hasNext()) {
        val num: Int = inputIterator.next().toInt()
        println("Number Added to Queue: $num")
        queue.enqueue(num)
    }

    println("Numbers are successfully added to the Queue.\nQueue currently has ${queue.size()} Elements.")

    // Starting Dequeue.
    while (!queue.isEmpty()) {
        println(queue.dequeue())
    }

    println("Dequeue Completed\n Queue currently has ${queue.size()} Elements")
}