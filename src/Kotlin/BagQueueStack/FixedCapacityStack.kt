package Kotlin.BagQueueStack

import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.IndexOutOfBoundsException
import java.util.*
import kotlin.collections.ArrayList

class FixedCapacityStack<Item>(stackSize: Int) : Iterable<Item?> {
    // Same as Java, Kotlin also does not allow to create Generic Array.
    private var arr: Array<Item?> = Array<Any?>(stackSize) {null} as Array<Item?>
    private var elementCount: Int = 0

    fun push(item: Item): Unit {
        if (elementCount == arr.size) resize(arr.size * 2)
        arr[elementCount++] = item
    }

    fun pop(): Item? {
        val returnItem: Item? = arr[--elementCount]
        arr[elementCount] = null    // Avoid Loitering
        //if (elementCount > 0 && elementCount == arr.size / 4) resize(arr.size / 2)
        return returnItem
        //"Resizing condition should be refactored."
    }

    fun size(): Int {
        return elementCount
    }

    fun isEmpty(): Boolean {
        return elementCount == null;
    }

    fun capacity(): Int {
        return arr.size - elementCount
    }

    fun resize(newStackSize: Int): Unit {
        if (newStackSize < elementCount) throw IllegalArgumentException()
        val temp: Array<Item?> = Array<Any?>(newStackSize) {null} as Array<Item?>
        for (index in arr.indices) {
            temp[index] = arr[index]
        }
        arr = temp
        TODO("Resizing function causes IndexOutOfBoudsException; Should be refactored")
    }
    override fun iterator(): Iterator<Item?> {
        return FixedCapacityStackIterator<Item>(arr)
    }

    private class FixedCapacityStackIterator<Item>(private val arr: Array<Item?>): Iterator<Item?> {
        private var index = arr.size - 1

        override fun hasNext(): Boolean {
            if (index < 0) throw StackOverflowError()
            return arr[index] != null
        }

        override fun next(): Item? {
            if (index < 0) throw StackOverflowError()
            if (arr[index] == null) throw NoSuchElementException()
            return arr[index--]
        }

    }
}

// Test Client
fun main(args: Array<String>) {
    val testStack: FixedCapacityStack<String> = FixedCapacityStack(10)
    val br = BufferedReader(InputStreamReader(System.`in`))
    var inputString: List<String> = br.readLine().split(" ")

    for (s: String in inputString) {
        if (!s.equals("-")) {
            testStack.push(s)
        } else {
            if (!testStack.isEmpty()) {
                print("${testStack.pop()} ")
            }
        }
    }

    println("(${testStack.size()} left on Stack.)")
}