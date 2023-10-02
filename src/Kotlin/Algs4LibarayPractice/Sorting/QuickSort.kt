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

    // Int Quicksort.
    Quicksort<Int>().run {
        this.sort(intArray)
        this.show(intArray)
    }

    // Int 3-way Quicksort.
//    Quicksort3way<Int>().run {
//        this.sort(intArray)
//        println("=============================================================")
//        this.show(intArray)
//    }


    br.close()
}

class Quicksort<T: Comparable<T>> : BasicSortOperations<T>() {
    override fun sort(a: MutableList<T>) {
        // Shuffle the original array to eliminate dependencies among the elements.
        a.shuffle()

        // Call private sort function to initiate a Quicksort
        sort(a, 0, a.size - 1)
//        improvedSort(a, 0, a.size - 1)

    }

    // Private sort function for Quicksort
    private fun sort(a: MutableList<T>, lo: Int, hi: Int) {
        if (hi <= lo) return
        // Partitioning
//        val j = partition(a, lo, hi)
        val j = partitionMedianOfThree(a, lo, hi)
        // Sort left subarray.
        sort(a, lo, j - 1)      // because the value j is the index of partitioning item.
        // Sort right subarray
        sort(a, j + 1, hi)      // because the value j is the index of partitioning item.
    }

    /**
     * 1. Improved Sort (Combination of Quicksort & Insertion Sort.)
     *   Idea: For tiny subarray, Insertion sort is faster than Quicksort.
     *   Approach: Once subarray reaches a pre-defined CUTOFF size, perform Insertion sort.
     */
    private fun improvedSort(a: MutableList<T>, lo: Int, hi: Int) {
        // CUTOFF value depends on the system, but normally, it is between 5 and 15.
        // Set cutoff value to 10.
        if (hi <= lo + 10) {
            Insertion().sort(a, lo, hi)
            return
        }
        // Partitioning
        val j = partition(a, lo, hi)
        // Sort left subarray.
        sort(a, lo, j - 1)      // Because the value j is the index of partitioning item.
        // Sort right subarray
        sort(a, j + 1, hi)      // Because the value j is the index of partitioning item.
        // Note that the partitioning item would never be moved. (would stay on its position.)

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
        val v = a[lo]

        // Left/Right scan indexes.
        var i = lo
        var j = hi + 1      // Reason: because of the method to be used for decrement.

        while (true) {
            // Scan from the left.
            while (less(a[++i], v)) if (i == hi) break   // To prevent the pointer would not go over the boundary.
            while (less(v, a[--j])) if (j == lo) break   // To prevent the pointer would not go over the boundary.
            if (i >= j) break       // break condition for most outer while loop
            // Exchange the i (greater than v) and j (less than v)
            exchange(a, i, j)
        }
        // Exchange a[lo] with j (rightmost entry of the left subarray)
        exchange(a, lo, j)

        // Return the index of j
        return j
    }

    /**
     * Use median of three to determine a patitioning element.
     */
    private fun partitionMedianOfThree(a: MutableList<T>, lo: Int, hi: Int): Int {
        // set partitioning element using media of three.
        val medianOfThree = median(lo, (lo + hi) / 2, hi)
        val v = a[medianOfThree]

        // Move partitioning element to the head of the index.
        a.run {
            this.removeAt(medianOfThree)
            this.add(lo, v)
        }

        // Initialize indexing values.
        var i = lo
        var j = hi + 1

        // Scanning element.
        while(true) {
            while (less(a[++i], v)) if (i == hi) break
            while (less(v, a[--j])) if (j == lo) break
            if (i >= j) break
            else {
                // At this point, a[i] is greater than v and a[j] is less than v, therefore, a[i] and a[j] should be
                // exchanged.
                exchange(a, i, j)
            }
        }
        // Put partitioning element to "rightmost position" of left array, which would be j.
        exchange(a, lo, j)

        return j
    }

    private fun median(a: Int, b: Int, c: Int): Int {
        val three = mutableListOf(a, b, c)

        if (three[0] > three[1]) {
            val temp = three[0]
            three[0] = three[1]
            three[1] = temp
        }
        if (three[1] > three[2]) {
            val temp = three[2]
            three[2] = three[1]
            three[1] = temp
        }
        if (three[0] > three[2]) {
            val temp = three[2]
            three[2] = three[0]
            three[0] = temp
        }

        return three[1]
    }

}

/**
 * Three-way quick sort.
 *   Faster than merge sort in practical situation (such as the array with multiple duplicated keys.)
 */
class Quicksort3way<T: Comparable<T>> : BasicSortOperations<T>() {
    override fun sort(a: MutableList<T>) {
        // Shuffle the array to avoid worst-case scenario. (Prevent unbalanced partitioning.)
        a.shuffle()

        // Call sort function that performs 3-way Quicksort.
        sort(a, 0, a.size - 1)
    }

    // Private function that performs 3-way Quicksort.
    private fun sort(a: MutableList<T>, lo: Int, hi: Int) {
        // If (hi is less or equal to lo, terminate the function.)
        if (hi <= lo) return
        // Initialize variables for 3-way Quicksort. (lt, i, gt)
        var lt = lo
        var i = lo + 1
        var gt = hi

        // Partitioning Element.
        val v = a[lo]

        // Scan the array until i becomes equal to gt.
        while (i <= gt) {
            // Direct comparison using function provided by Comparable interface.
            val cmp = a[i].compareTo(v)
            // Case 1: Where a[i] is less than v.
            if (cmp < 0) exchange(a, lt++, i++)     // Exchange a[lt]] and a[i] and increase both index.
            // case 2: Where a[i] is greater than v.
            else if (cmp > 0) exchange(a, i, gt--)  // Exchange a[i] and a[gt] and decrease index gt.
            // Case 3: Where a[i] is equal to v.
            else i++                                // Won't perform exchange, and just increase index i.
        }

        // At this point,the status of array would like below:
        // a[lo..lt-1] < v = a[lt..gt] < a[gt + 1..hi]

        // Do same process for each left and right subarrays.
        sort(a, lo, lt - 1)
        sort(a, gt + 1, hi)
    }

}



