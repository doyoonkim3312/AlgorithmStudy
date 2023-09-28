package Kotlin.Algs4LibarayPractice.Sorting

import edu.princeton.cs.algs4.StdRandom
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.random.Random

/**
 * Faster Sorting Algorithm based on the fundamental algorithms.
 */

fun main() {
//    val br = BufferedReader(InputStreamReader(System.`in`))
//    val array = br.readLine().split(" ").toMutableList()

    // Test case for Exercise 2.1.12
    val array = MutableList<Double>(100000) { 0.0 }
    for (i in 0 until array.size) {
        array[i] = Random.nextDouble(0.0, 1.0)
    }

    val shellsort = Shellsort()
    shellsort.sort(array)
//    shellsort.show(array)

//    br.close()
}

/**
 * Shellsort
 * Shellsort is an extension of Insertion sort. Shellsort improves its speed by allowing 'exchange' to be happened
 * for array entries that are far apart, to produce 'partially sorted' array.
 *
 * Essence: Make subsequent array of each length h, and perform Insertion Sort for each independent subsequent arrays.
 *   Why this would be fast? It's because the Insertion Sort is hardly depend on the initial input, and by performing
 *   Shellsort, the general array would become sorted array with 'partially sorted arrays'
 */

class Shellsort : SortOperations(){
    override fun <T : Comparable<T>> sort(a: MutableList<T>) {
        println("Starting Shellsort...")
        // Sort given a in increasing order.
        val n = a.size
        var h = 1       // String at the smallest increment.

        // Variables for exercise 2.1.12
        var totalCompares = 0

        // Decide the value of h to be used as starting h
        while (h < n/3) h = 3*h + 1

        // Decrease h to 1.
        while (h >= 1) {
            // H-sorted array.
            for (i in h until n) {
                // Insert a[i] among a[i-h] a[i - 2*h] ...
                for(j in i downTo h) {
                    if (less(a[j], a[j - h])) {
                        exchange(a, j, j - h)
                    }
                    totalCompares++
                }
            }
            h = h / 3
        }
        println("Sorted.")

        // Result statement for Exercise 2.1.12
        println("Analysis (Exercise 2.1.12)\nCalculated Constant n: ${totalCompares / n}")
    }

}