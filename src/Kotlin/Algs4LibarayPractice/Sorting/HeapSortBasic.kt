package Kotlin.Algs4LibarayPractice.Sorting

import Kotlin.RandomDataGenerator

/**
 * Sorting Basics - HeapSort
 *
 */

fun main() {
    //    val stringSample = RandomDataGenerator.getStringArray(8)
    //    val stringSample = arrayOf("S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E")
    val stringSample = arrayOf("U", "S", "Q", "O", "S", "I", "A", "J")
    BasicHeapsort(array = stringSample).run {
        this.sort()
        this.show()
    }
}

/**
 * Basic Heapsort
 *      Heapsort consists of two phases: 1. Heap Construction 2. Sortdown
 *      1. Heap Construction: Construct heap as arbitrary order.
 *      2. Sortdown: Pops out Max items Min items Uses functions swim() and sink() directly to construct heap-sorted structure.
 *
 *      This examples uses String type as key.
 *
 *      Since the heap uses Max Heap, the result would be sorted array in increasing order.
 */

class BasicHeapsort(val array: Array<String>) {

    private val pq: Array<String?>
    private var n = 0

    init {
        // Heap construction
        pq = Array(array.size + 1) {
            if (it == 0) null
            else {
                array[it - 1]
            }
        }
        n = array.size
    }

    fun sort() {
        // Heap construction (Use top-down reheapify)
        for (k in n / 2 downTo 1) {
            sink(k)
        }

        // Sortdown
        // From right to left
        while (n > 1) {
            // delMax
            exchange(1, n--)        // exchange the largest one with the very last key. (same as delMax operation.)
            sink(1)             // Now current status of heap violates the condition of Heap. Need to be reheapify.
        }
    }

    fun isEmpty(): Boolean {
        return n == 0
    }

    // Functions for reheapify
    fun swim(tarIdx: Int) {
        var k = tarIdx
        // Bottom-up
        while (k > 1 && !less(k, k / 2)) {
            exchange(k , k / 2)
            k = k / 2
        }
    }

    fun sink(tarIdx: Int) {
        var k = tarIdx

        while (2 * k <= n) {
            var j = 2 * k

            // Test children first.
            if (j < n && less(j, j + 1)) j++
            if (!less(k, j)) break

            exchange(k, j)
            k = j
        }
    }

    // Less function.
    fun less(i: Int, j: Int): Boolean {
        return pq[i]!!.compareTo(pq[j]!!) < 0
    }

    // Exchange function
    fun exchange(i: Int, j: Int) {
        val temp = pq[i]
        pq[i] = pq[j]
        pq[j] = temp
    }

    fun show() {
        println("========== RESULT ==========")
        for (index in pq.indices) {
            if (index != 0) {
                print("${pq[index]} ")
            }
        }
    }

}