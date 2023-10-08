package Kotlin.Algs4LibarayPractice.Sorting

import Kotlin.RandomDataGenerator
import edu.princeton.cs.algs4.In
import java.io.File
import java.util.Scanner
import kotlin.random.Random

/**
 * PRIORITY QUEUE BASIC
 *    Core idea: "Remove Maximum & Insert"
 *
 *    Priority Queue in the implementation below is based on the data structure "Binary Heap". Binary Heap refers to
 *    a complete binary tree which is used to store data efficiently to get the max or min element based on its structure.
 *
 *
 */

fun main(args: Array<String>) {
    //

//    val randomString = RandomDataGenerator.getStringArray(100)
//
//    MaxPQ(max = randomString.size).run {
//        for (key in randomString) {
//            this.insert(key)
//        }
//
//        // Print out the sorted array (descending order) by popping out the largest key.
//        while (!this.isEmpty()) {
//            print("${this.delMax()} ")
//        }
//        println()
//    }

    // Indexed PQ Client Example - Multiway.
    val n = args.size

    val streams = Array<In>(n) {
        In(args[it])
    }

    Multiway.merge(streams)

}



// Basic Heap-based Priority Queue
// Example; for type String.
// Example of more generic approach is described in Java > Sorting > MaxPQ.java.
class MaxPQ {
    // Required Properties.
    // Binary Heap (Using non-pointer approach)
    private lateinit var pq: Array<String?>          // Use immutable list instead.
    private var n = 0                            // Index variable to traverse PQ.

    // Constructor for creating empty PQ
    constructor() {
        // DO NOTHING.
    }
    // Constructor for user defined capacity
    constructor(max: Int) {
        // Un-use pq[0], and start heap from index 1 until n.
        pq = Array(max + 1) {
            null
        }
    }
    // Constructor for creating PQ from the keys in array.
    constructor(a: MutableList<String>) {

    }

    // Necessary operations.
    fun insert(v: String) {
        // Insert new element, reheapify element using swim
        pq[++n] = v
        swim(n)
    }

//    fun max(): Key {
//        return pq[1]
//    }

    fun delMax(): String {
        // Pop element at the top, exchange with the very last element, and reheapify using sink.
        val result = pq[1]
        exchange(1, n--)
        pq[n + 1] = null

        sink(1)

        return result!!
    }

    fun isEmpty(): Boolean {
        return n == 0
    }

    fun size(): Int {
        return n
    }

    // Less function. (Using compareTo function provided by Comparable interface.)
    // Key would be accessed under less and exchange functions ONLY.
    private fun less(i: Int, j: Int): Boolean {
        return pq[i]!!.compareTo(pq[j]!!) < 0
    }

    //  exchange function.
    // Key would be accessed under less and exchange functions ONLY.
    private fun exchange(i: Int, j: Int) {
        val temp = pq[i]
        pq[i] = pq[j]
        pq[j] = temp
    }

    /**
     * Reheapify: Restoring heap (binary heap) to make condition of heap to be satisfied.
     *
     *  Condition of Heap could be violated for mainly two reason:
     *      1. priority of some node is increased (new node is added at the bottom)
     *      2. priority of some node is decreased. (new node is added at the top (root or top of smaller node.)
     *
     *      Solution:
     *      1. Bottom-up Rehepify: "Exchange the node with its parent, until the condition becomes to be satisfied.
     *      2.
     */

    // Bottom-up Reheapify
    private fun swim(i: Int) {
        // k would be the index of newly added node.
        var k = i
        while (k > 1 && less(k / 2, k)) {
            // Loop conditions:
            // 1. k > 1: to prevent IndexOutOfBounds. (binary heap - pq - in used is in range of 1..n)
            // 2. less(k / 2, k): when k / 2 (parent) smaller than k (newly added node). those should be
            //                    exchanged until the condition of heap to be satisfied.
            exchange(k, k / 2)
            k = k / 2           // Change index k to newly positioned index.
        }
    }

    // Top-down Reheapify
    private fun sink(i: Int) {
        // k would be the index of newly added node.
        var k = i

        // Top-down ==> the index of children nodes are 2k and 2k + 1
        while (2 * k <= n) {
            // Loop condition: prevent IndexOutOfBounds exception.
            // Test out current node with both children.

            // Test children first. (decided which one to be exchanged)
            var j = 2 * k
            // Condition:
            // 1. j < n: Prevent IndexOutOfBounds
            // 2. less(j, j + 1): Compare children first to decided which one would be exchanged.
            if (j < n && less(j, j + 1)) j++
            if (!less(k, j)) break      // Break condition for outer while loop. (Heap condition satisfied.)

            exchange(k, j)
            k = j
        }
    }

    // Auxiliary Function
    fun show() {
        for (element in pq) {
            println(element)
        }
    }

}

// IndexMinPQ (Basic PQ approach with extendable Array, Immutable Key(optional), Index PQ.
// The example below uses type String for Key type.
class IndexMinPQ {
    // necessary elements.
    private lateinit var pq: Array<Int>         // PQ that holds Indices. (Binary Heap using 1-based indexing.
    private lateinit var qp: Array<Int>         // inverse pq. This guarantees pq[pq[i]] = pq[qp[i]] = i
    private lateinit var keys: Array<String?>   // Holds actual keys.
    private var n = 0                           // indexing variable for pq.

    // Empty Constructor
    constructor() {

    }
    // Secondary Constructor. (Accepts arbitrary size of the pq array for initialization.)
    constructor(max: Int) {
        pq = Array(max + 1) {
            0
        }
        qp = Array(max + 1) {
            -1
        }
        keys = Array(max + 1) {
            null
        }
    }

    constructor(a: Array<String>) {
        for (index in a.indices) {
            // Copy current array into local private PQ.
        }
    }

    // Auxiliary functions.
    fun isEmpty(): Boolean {
        return n == 0
    }

    fun size(): Int {
        return n
    }

    // Insert
    fun insert(idx: Int,key: String) {
        n++
        pq[idx] = n
        qp[n] = idx

        keys[idx] = key
        swim(n)
    }

    /**
     * Change the key associated with [idx] to [key]
     */
    fun changeKey(idx: Int, key: String) {
        keys[idx] = key
        // Restore Heap condition.
        // Note: Due to the property of inverse array, pq[qp[idx]] = idx is ensured.
        swim(qp[idx])
        sink(qp[idx])
    }

    /**
     * Is index [idx] associated with some key?
     */
    fun contains(idx: Int): Boolean {
        return keys[idx] != null
    }

    /**
     * Remove [tarIdx] and its associated key.
     * Note: Reheapify might be needed.
     */
    fun delete(tarIdx: Int) {
        /*
        keys[pq[tarIdx]] = null

        // exchange target with the very bottom one.
        exchange(qp[tarIdx], n--)
        qp[pq[n + 1]] = -1
//        pq[n + 1] = 0

        // Exchange position with the very bottom one refers that the exchanged item violates conditions of MinPQ
        // because the "larger" key is being placed (promoted) to upper level. (its children might be smaller than itself)
        sink(qp[tarIdx])
         */

        // Textbook Solution
        val index = qp[tarIdx]      // pq[qp[tarIdx]] ==> tarIdx.

        exchange(index, n--);

        // Reheapify
        swim(index)
        sink(index)

        keys[tarIdx] = null
        qp[tarIdx] = -1
    }

    /**
     * return a minimal key
     */
    fun minKey(): String {
        return keys[pq[1]]!!
    }

    fun minIndex(): Int {
        return pq[1]
    }

    /**
     * Remove a minimal key and return its index.
     */
    fun delMin(): Int {
        // delete function can be used.
        val minIndex = pq[1]
//        delete(1)
        // Textbook answer.
        exchange(1, n--)
        sink(1)

        keys[pq[n + 1]] = null      // Prevent Loitering
        qp[pq[n + 1]] = -1

        return minIndex
    }

    /**
     * return [key] associated with index [idx]
     */
    fun keyOf(idx: Int): String {
        return keys[idx]!!
    }


    // Exchange & greater
    fun greater(i: Int, j: Int): Boolean {
        return pq[i]!!.compareTo(pq[j]!!) > 0
    }

    fun exchange(i: Int, j: Int) {
        val temp = pq[i]
        pq[i] = pq[j]
        pq[j] = temp
    }

    // Swim & Sink
    fun swim(tarIdx: Int) {
        var k = tarIdx
        while (k > 1 && greater(k / 2, k)) {
            exchange(k / 2, k)
            k = k / 2
        }
    }

    fun sink(tarIdx: Int) {
        var k = tarIdx
        while (2 * k < n) {
            var j = 2 * k
            if (j < n && greater(j, j + 1)) j++
            if (!greater(k, j)) break

            exchange(k, j)
            k = j
        }
    }

}

/**
 * IndexMinPQ Client Example [Multiway]
 */
class Multiway {
    companion object {
        fun merge(streams: Array<In>) {
            val n = streams.size

            val pq = IndexMinPQ(max = n)

            // Insert
            for (i in 0 until n) {
                if (!streams[i].isEmpty) {
                    pq.insert(i, streams[i].readString())
                }
            }

            while(!pq.isEmpty()) {
                // Remove Minimum
                print("${pq.minKey()} ")
                val i = pq.delMin()     // value i follows the number of input stream (m1 = 0, m2 = 1, m3 = 2)

                if (!streams[i].isEmpty) {
                    pq.insert(i, streams[i].readString())
                }
            }

        }
    }
}