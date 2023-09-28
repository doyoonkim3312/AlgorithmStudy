package Kotlin.Algs4LibarayPractice.Sorting

import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*
import kotlin.math.min

/**
 * Sorting Basic (Elementary Sorting)
 *
 *   Sorting: The process of rearranging a sequence of objects so as to put them in some logical order.
 *     Objective: Rearrange the items such that their KEYS are ordered according to some well-defined ordering rule.
 *
 */

fun main() {
    val br = BufferedReader(InputStreamReader(System.`in`))

    val givenString = br.readLine().split(" ")
    val list = givenString.toMutableList()

    // Selection Sort
//    val selectionSort = Selection()
//    selectionSort.sort(list)
//    selectionSort.show(list)

    // Insertion Sort
    val insertionSort = Insertion()
    insertionSort.sort(list)
    insertionSort.show(list)


    br.close()
}

/**
 * Template for Sort-type classes.
 * Example (For type String, which is a child class of Comparable class)
 */
abstract class SortOperations {
    abstract fun <T: Comparable<T>> sort(a: MutableList<T>)

    fun <T: Comparable<T>> less(v: T, w: T): Boolean {
        return v.compareTo(w) < 0
    }

    fun <T: Comparable<T>> exchange(a: MutableList<T>, i: Int, j: Int) {
        val temp = a[i]
        // exchange character at i with j
        a[i] = a[j]
        a[j] = temp
    }

    fun <T: Comparable<T>> show(a: MutableList<T>) {
        for (character in a) {
            print("$character ")
        }
        println()
    }

    fun <T: Comparable<T>> isSorted(a: MutableList<T>): Boolean {
        for (index in a.indices) {
            if (index == 0) continue
            else {
                if (less(a[index], a[index - 1])) return false
            }
        }
        return true
    }

}

/**
 * Basic Sorting Operation.
 */
abstract class BasicSortOperations<T: Comparable<T>> {
    // sort
    abstract fun sort(a: MutableList<T>)

    // less
    fun less(v: T, w: T): Boolean {
        return v.compareTo(w) < 0
    }

    // exchange
    fun exchange(a: MutableList<T>, i: Int, j: Int) {
        val temp = a[i]

        // Exchange elements
        a[i] = a[j]
        a[j] = temp
    }

    // show
    fun show(a: MutableList<T>) {
        for (element in a) {
            print("$element ")
        }
        println()
    }

    // isSorted
    fun isSorted(a: MutableList<T>): Boolean {
        for (i in a.indices) {
            if (i == 0) continue
            if (!less(a[i], a[i - 1])) return false
        }

        return true
    }

}


/**
 * Selection Sort
 *   Simplest sorting approach. It keeps selecting entry for sorting, therefore it is named as 'Selection' sort.
 *   Flow:
 *   1. Find the smallest item in the array.
 *   2. Exchange it with the very first entry.
 *   3. Find next smallest item in the array.
 *   4. Exchange it with the second entry.
 *   5. Repeat above steps until the array is fully sorted.
 */
class Selection : SortOperations(){
    override fun <T : Comparable<T>> sort(a: MutableList<T>) {
        // Attempt 1: Use nested for loop
        for (i in 0 until a.size) {
            var min = i     // current index of minimum value to be compared.
            for (j in i + 1 until a.size) {
                // Compare entry with following element using 'less' method.
                if (less(a[j], a[min])) {
                    // if a[j] is less than a[i] --> need to be exchanged.
//                        exchange(a, i, j)       // This approach cause multiple unnecessary exchange call.
                    min = j
                }
            }
            exchange(a, i, min)
        }
    }

}

/**
 * Insertion Sort
 * Just like shuffling the deck of cards using bridge hands. In a computer implementation, it is required
 * to make space to insert the current item by moving larger items one position to the right, before
 * inserting the current item into the vacated position.
 *
 *   Very Simple Diagram
 *   Initial: O R S T E
 *     Step 1: Compare E and T --> Exchange E and T.
 *     Step 2: Compare E and S --> Exchange E and S.
 *     Step 3: Compare E and R --> Exchange E and R.
 *     Step 4: Compare E and O --> Exchange E and O.
 */
class Insertion : SortOperations() {
    override fun <T : Comparable<T>> sort(a: MutableList<T>) {
        // Test for Proposition C in p.252
        var exchangeCount = 0
        var comparisonCount = 0
        for (i in 1 until a.size) {
            for (j in i downTo 1) {
                comparisonCount++
                if (less(a[j], a[j - 1])) {
                    exchange(a, j, j - 1)
                    exchangeCount++
                }
            }
        }

        // Test for Proposition C in p.252
//        println("Test for proposition C in p.252\nTotal Exchange: $exchangeCount Total Comparison: $comparisonCount")
    }

}
