package Kotlin.BagQueueStack

import java.lang.NullPointerException
import java.util.*
import kotlin.NoSuchElementException

// Simple LIFO Stack Implementation

public class Stack<Item>: Iterable<Item> {
    private var size: Int = 0
    private var current: Node<Item>? = null

    private class Node<Item>(var data: Item, var pointer: Node<Item>?)

    /**
     * Add new element to the stack, under the LIFO policy.
     */
    fun push(data: Item): Unit {
        if (current == null) {
            current = Node<Item>(data, null)
            size++
        } else {
            val temp: Node<Item>? = current
            current = Node<Item>(data, null)
            current?.pointer = temp
            size++
        }
    }

    /**
     * Remove and return the most recently added element from stack.
     * @return Most recently added element.
     * @exception NoSuchElementExcepton will be thrown if there is no more element left on stack.
     * @exception NullPointerException will be thrown if returned vale is null.
     */
    fun pop(): Item {
        if (current == null) throw NoSuchElementException()
        val temp: Node<Item>? = current
        current = current?.pointer
        size--
        return temp!!.data
    }

    /**
     * Check stack is currently empty or not.
     * @return true, if stack is currently empty, false, if stack has more value to access.
     */
    fun isEmpty(): Boolean {
        return size() == 0
    }

    /**
     * Return size of current stack.
     * @return size of current stack.
     */
    fun size(): Int {
        return size
    }

    override fun iterator(): Iterator<Item> {

        class LinkedStackIterator(var node: Node<Item>?): Iterator<Item> {

            /**
             * Check whether iterator has more value to access.
             * @return ture, if iterator has next value to access, false, if iterator has no more value to access.
             */
            override fun hasNext(): Boolean {
                return node?.pointer != null
            }

            /**
             * Return next value in iterator.
             * @return next element in this iterator.
             * @exception NullPointerException will be thrown, if there are no more elements to access, or, if return
             * value has a null value.
             */
            override fun next(): Item {
                if (!hasNext()) throw NullPointerException()
                val temp: Node<Item>? = node
                node = node?.pointer
                return temp!!.data
            }
        }
        return LinkedStackIterator(current)
    }

}

// Client Code for Unit Test.
fun main(args: Array<String>) {
    // dijkstraTwoStackAlgorithm(args)
    stackOrderTest(args)
}

fun dijkstraTwoStackAlgorithm(args: Array<String>) {
    val operator: Stack<String> = Stack<String>()
    val operand: Stack<Double> = Stack<Double>()
    val sc = Scanner(System.`in`)
    val inputString: List<String> = sc.nextLine().split(" ")
    sc.close()

    for (s: String in inputString) {
        if (s.equals("("));
        else if (s.equals("+")) operator.push(s)
        else if (s.equals("-")) operator.push(s)
        else if (s.equals("/")) operator.push(s)
        else if (s.equals("*")) operator.push(s)
        else if (s.equals("sqrt")) operator.push(s)
        else if (s.equals(")")) {
            var num = operand.pop()
            val poppedOperator = operator.pop()
            if (poppedOperator.equals("+")) num += operand.pop()
            else if (poppedOperator.equals("-")) num -= operand.pop()
            else if (poppedOperator.equals("/")) num /= operand.pop()
            else if (poppedOperator.equals("*")) num *= operand.pop()
            operand.push(num)
        }
        else {
            try {
                operand.push(s.toDouble())
            } catch (ime: InputMismatchException) {
                println(ime.stackTrace)
            } catch (e: Exception) {
                println(e.stackTrace)
            }
        }
    }
    println("Result: ${operand.pop()}")
}

fun stackOrderTest(args: Array<String>) {
    val stack: Stack<String> = Stack<String>()
    val sc = Scanner(System.`in`)
    var inputString: Iterator<String> = sc.nextLine().split(" ").iterator()
    while (inputString.hasNext()) {
        var s = inputString.next()
        if (!s.equals("-")) {
            stack.push(s)
        } else if (!stack.isEmpty()) {
            print(stack.pop() + " ")
        }
    }

    println("(${stack.size()} left on stack.")
}