package Kotlin.Algs4LibarayPractice.Sorting

import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.random.Random

fun main() {
    val br = BufferedReader(InputStreamReader(System.`in`))

    val stringArray = br.readLine().split(" ").toMutableList()

    // Generate int array
//    val size = 1000
//    val intArray = MutableList<Int>(size) { 0 }
//
//    for (i in 0 until size) {
//        intArray[i] = Random.nextInt(10000000)
//    }

    // Top-down Mergesort.
//    val mergeSort = TopDownMergesort<String>()
////    mergeSort.merge(array, 0, (array.size / 2) -1, array.size - 1)
//    mergeSort.sort(stringArray)
//
//    mergeSort.show(stringArray)

    // Bottom Up Mergesort.
    val bottomUpMergesort = BottomUpMergesort<String>()
    bottomUpMergesort.sort(stringArray)

    bottomUpMergesort.show(stringArray)

    br.close()
}

/**
 * Merge sort
 *  Merge sort basically uses a simple operation called 'Merge,' which refers to a combining two ORDERED arrays
 *  to make one larger ORDERED array.
 *
 *      "IT GUARANTEES TO SORT ANY ARRAY OF N TIMES IN TIME PROPORTIONAL TO NlogN"
 */
class AbstractMerge : SortOperations() {
    override fun <T : Comparable<T>> sort(a: MutableList<T>) {
        TODO("Not yet implemented")
    }

    fun <T: Comparable<T>> merge(a: MutableList<T>, lo: Int, mid: Int, hi: Int) {
        // Merge a[lo..mid] with a[mid+1..hi]
        // Index values for each halves
        var i = lo          // First half
        var j = mid + 1     // Second half

        // Auxiliary Array.
        // Copy a[lo..hi] to aux[lo..hi]
        val aux = MutableList<T>(a.size) { a[it] }

        // Merge back to a[lo..hi]
        for (k in lo..hi) {
            if (i > mid) a[k] = aux[j++]
            else if (j > hi) a[k] = aux[i++]
            else if (less(aux[j], aux[i])) a[k] = aux[j++]      // j: right, i: left.
            else a[k] = aux[i++]
        }
    }

}

/**
 * Top-down Mergesort.
 *  Recursive mergesort implementation that is well-known as the most popular example of "Divide & Conquer"
 *  This algorithm proofs that if it sorts the two subarrays, it sorts the whole array by merging together the
 *  subarrays.
 */

class TopDownMergesort<T: Comparable<T>> : BasicSortOperations<T>(){

    private lateinit var aux: MutableList<T>

    override fun sort(a: MutableList<T>) {
        aux = MutableList<T>(a.size) { a[it] }

        // Calling sort recursively.
//        this.sort(a, 0, a.size - 1)

        // Improved sort. (10 to 15 percent faster.
        this.improvedSort(a, 0, a.size - 1)

    }

    // Sort for Top-down Mergesort
    private fun sort(a: MutableList<T>, lower: Int, upper: Int) {
        // Concept: Divide & Conquer.
        if (upper <= lower) return

        // Index of 'mid' which divides array into two subarrays.
        val mid = lower + (upper - lower) / 2

        // Sort left half by calling this function recursively
        sort(a, lower, mid)
        // Sort right half by calling this function recursively
        sort(a, mid + 1, upper)

        // At this point, subarrays have been sorted. Call merge to merge two subarrays.
        merge(a, lower, mid, upper)
    }

    private fun improvedSort(a: MutableList<T>, lower: Int, upper: Int) {
        // Main concept: Divide & Conquer.
        val size = upper - lower + 1
        if (size <= 15) {
            // for tiny array, use Insertion Sort.
            Insertion().sort(a)
            return
        }

        // Index of 'mid' which divides array into two subarrays.
        val mid = lower + (upper - lower) / 2

        // Sort left half by calling this function recursively
        improvedSort(a, lower, mid)
        // Sort right half by calling this function recursively
        improvedSort(a, mid + 1, upper)

        // At this point, subarrays have been sorted. Call merge to merge subarrays.
        merge(a, lower, mid, upper)
    }

    fun merge(a: MutableList<T>, lo: Int, mid: Int, hi: Int) {
//        println("Current: $a")      // For test.
        // Index values for each halves.
        var i = lo
        var j = mid + 1

        // Auxiliary Array ( Copy a[lo.hi] to aux[lo..hi]
        for (k in lo..hi) {
            aux[k] = a[k]
        }

        for (k in lo..hi) {
            if (i > mid) a[k] = aux[j++]
            else if (j > hi) a[k] = aux[i++]
            else if (less(aux[j], aux[i])) a[k] = aux[j++]      // Comparison
            else a[k] = aux[i++]
        }
    }

}

/**
 * Bottom-up Mergesort
 *      Bottom-up mergesort is a prototypical of 'divide and conquer' as well.
 *
 *      Process
 *      1. Organize the merges so that we do all the merges of tiny subarrays on one pass.
 *      2. Continuing until we do a merge that encompasses the whole array.
 *
 *      All merges involve subarrays of equal length, doubling the sorted subarray length for
 *      the next pass.
 */
class BottomUpMergesort<T: Comparable<T>> : BasicSortOperations<T>() {

    private lateinit var aux: MutableList<T>
    override fun sort(a: MutableList<T>) {
        val n = a.size

        // Initialize aux for the first time.
        aux = MutableList<T>(n) { a[it] }

        // Perform BU Mergesort.
        var len = 1       // Starting from subarray with length of 1.
        while (len < n) {
            for (lo in 0 until n - len step len+len) {
                // Call merge function for perform merge operation.
                merge(a, lo, lo + len - 1, Math.min(lo+len+len - 1, n - 1))
            }
            len *= 2      // Doubling the length of subarray
        }

    }

    fun merge(a: MutableList<T>, lo: Int, mid: Int, hi: Int) {
        // Index variables for merge operation.
        var i = lo
        var j = mid + 1

        // Copying a[lo..hi] to aux[lo..hi]
        for (k in lo..hi) {
            aux[k] = a[k]
        }

        // Merge back to subarrays to one large array. (Also performs comparison as well)
        for (k in lo..hi) {
            if (i > mid) a[k] = aux[j++]
            else if (j > hi) a[k] = aux[i++]
            else if (less(aux[j], aux[i])) a[k] = aux[j++]
            else a[k] = aux[i++]
        }
    }



}