package Kotlin.Algs4LibarayPractice.Sorting

import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.random.Random

/**
 * Quicksort
 *      Quicksort is one of the widely used sorting algorithm, because of the following reasons.
 *          1. It works well for a variety of different kinds of input data.
 *          2. It is substantially faster than any other sorting method in typical applications.
 *          3. It has relatively smaller inner loop than most other sorting algorithms.
 *
 *      Quicksort is also a type of 'divide-and-conquer.' It works by 1) partitioning an array into two subarrays,
 *      then 2) sorting the subarrays independently.
 *      For quicksort, we rearrange the array such that, when the two subarrays are sorted, the whole array is ordered.
 */
fun main() {
    val br = BufferedReader(InputStreamReader(System.`in`))

    // Test with String array first.
//    val stringArray = br.readLine().split(" ").toMutableList()
//    val stringQuicksort = Quicksort<String>()
//
//    stringQuicksort.sort(stringArray)
//    stringQuicksort.show(stringArray)

    // Test with randomly generated Int array.
    // Generate int array
    val size = 10
    val intArray = MutableList<Int>(size) { 0 }

    for (i in 0 until size) {
        intArray[i] = Random.nextInt(1000)
    }
    println("Generated array:\n\t$intArray")

    Quicksort<Int>().run {
        this.sort(intArray)
        this.show(intArray)
    }


    br.close()
}

class Quicksort<T: Comparable<T>> : BasicSortOperations<T>() {
    override fun sort(a: MutableList<T>) {
        // Shuffle the original array to eliminate dependencies among the elements.
        a.shuffle()

        // Call private sort function to initiate a Quicksort
        sort(a, 0, a.size - 1)
    }

    // Private sort function for Quicksort
    private fun sort(a: MutableList<T>, lo: Int, hi: Int) {
        if (hi <= lo) return
        // Partitioning
        val j = partition(a, lo, hi)
        // Sort left subarray.
        sort(a, lo, j - 1)      // because the value j is the index of partitioning item.
        // Sort right subarray
        sort(a, j + 1, hi)      // because the value j is the index of partitioning item.
    }

    /**
     * [function] partition
     *   Basic Strategy:
     *   1. Choose a[lo] arbitrarily as partitioning item.
     *   2. Starting from left end, scan the array until find the item greater than the partitioning item.
     *   3. Starting from right end, scan the array until find the item less than the partitioning item.
     *   4. Exchange those selected entries.
     *   5. Exchange a[lo] with the rightmost entry of the left subarray (a[j]) and return index j.
     */
    private fun partition(a: MutableList<T>, lo: Int, hi: Int): Int {
        // partitioning item
        val v= a[lo]

        // Left/Right scan indexes.
        var i = lo
        var j = hi + 1      // Reason: because of the method to be used for decrement.

        while (true) {
            // Scan from the left.
            while(less(a[++i], v)) if (i == hi) break   // To prevent the pointer would not go over the boundary.
            while(less(v, a[--j])) if (j == lo) break   // To prevent the pointer would not go over the boundary.
            if (i >= j) break       // break condition for most outer while loop
            // Exchange the i (greater than v) and j (less than v)
            exchange(a, i, j)
        }
        // Exchange a[lo] with j (rightmost entry of the left subarray)
        exchange(a, lo, j)

        // Return the index of j
        return j
    }

}



