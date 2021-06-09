package Kotlin.BagQueueStack

import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*
import kotlin.NoSuchElementException

class Bag<Item>(): Iterable<Item?> {
    private var size: Int = 0
    private var first: Node<Item>? = null

    private class Node<Item> {
        var item: Item? = null;
        var next: Node<Item>? = null    // Recursively construct link (eventually constructs chain of nodes)
    }

    fun add(item: Item): Unit {
        val temp: Node<Item> ?= first
        first = Node()
        first!!.item = item
        first!!.next = temp
        size++
    }

    override fun iterator(): Iterator<Item?> {
        // Return Iterator that traverse items in LIFO order. (For Bag Implementation, order doesn't matter.)
        class BagIterator(private var current: Node<Item>?): Iterator<Item?> {
            override fun hasNext(): Boolean {
                return current != null
            }

            override fun next(): Item? {
                if (!hasNext()) throw NoSuchElementException()
                val returnItem: Item? = current?.item
                current = current?.next
                return returnItem
            }
        }
        return BagIterator(first)
    }
}

fun main(args: Array<String>) {
    val testBag: Bag<String> = Bag<String>()
    val sc: Scanner = Scanner(System.`in`)
    val inputString: List<String> = sc.nextLine().split(" ")

    for (s: String in inputString) {
        testBag.add(s)
    }

    val bagIterator: Iterator<String?> = testBag.iterator()
    while (bagIterator.hasNext()) {
        println(bagIterator.next())
    }
}