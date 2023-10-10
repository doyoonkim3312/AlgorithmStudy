package Kotlin.Algs4LibraryPractice.Sorting.ChapterApplication

import java.io.File
import java.util.*
import kotlin.Comparator
import kotlin.random.Random


/**
 * Application; Chapter 2.
 *  Key concepts:
 *      1. Pointer Sorting: The array contains references to the objects to be sorted, not the objects themselves.
 *      2. Immutable Keys: It is wise to ensure that KEY values do not change by using immutable key. Because keys are
 *      pointers to objects, and, as mentioned above, array contains references to the objects to be sorted.
 *      3. Exchanges are inexpensive: The reference approach makes the cost of an exchange roughly equal to the cost of
 *      a compare fore general situations involving arbitrarily large items.
 *
 */

fun main() {
    val scanner = Scanner(File("src/Kotlin/Algs4LibraryPractice/Sorting/ChapterApplication/sampleTransactions.txt"))

    val testSample = mutableListOf<Transaction>()

    while (scanner.hasNext()) {
        val data = scanner.nextLine().split(" ")
        testSample.add(
            Transaction(customer = data[0], where = data[1], date = Date.parseDate(data[2]), amount = Random.nextInt(1000).toDouble())
        )
    }

//    performMaxPQ(testSample)
//    performQuicksort(testSample)
    performInsertionSort(testSample)

    scanner.close()

}

// Perform sorting by MaxPQ class.
fun performMaxPQ(a: MutableList<Transaction>) {
    // MaxPQ.
    MaxPQ(a.size, Transaction.Companion.WhereOrder()).run {
        val sorted = this.sort(a)

        for (element in sorted) {
            println(element)
        }
    }
}

// Perform sorting by Quicksort.
fun performQuicksort(a: MutableList<Transaction>) {

    a.shuffle()
    val transactionArray = Array<Transaction>(a.size) {
        a[it]
    }

    TransactionQuicksort(Transaction.Companion.WhereOrder()).run {
        this.sort(transactionArray)
        this.show(transactionArray)
    }
}

// Perform sorting by Insertion Sort (Stable; refers to textbook p.342).
fun performInsertionSort(a: MutableList<Transaction>) {
    a.shuffle()
    val transactionArray = Array<Transaction>(a.size) {
        a[it]
    }

    TransactionInsertionSort(Transaction.Companion.WhereOrder()).run {
        this.sort(transactionArray)
        this.show(transactionArray)
    }
}


/**
 * MaxPQ for demonstrate sorting using Priority Queue.
 */
class MaxPQ(val size: Int, val comparator: Comparator<Transaction>) {
    // PQ related variables
    private val pq: Array<Transaction?> = Array(size + 1) {
        null
    }
    private var n = 0

    fun sort(a: List<Transaction>): List<Transaction?> {
        for (key in a) {
            insert(key)
        }

        return List<Transaction?>(a.size) {
            if (!this.isEmpty()) {
                this.delMax()
            } else {
                null
            }
        }

    }

    // Priority Queue: Insert & remove maximum/minimum
    fun insert(key: Transaction) {
             // Increase index (using 1-indexed array for priority queue)
        pq[++n] = key
        // perform swim
        swim(n)
    }

    fun delMax(): Transaction {
        val result = pq[1]
        // exchange result with the very last leaf.
        exchange(1, n--)
        // prevent loitering
        pq[n + 1] = null
        sink(1)

        return result!!
    }

    fun isEmpty(): Boolean {
        return n == 0
    }

    /**
     * less
     * @param [i] [j]: Index of target keys
     * @return [true] when key in [i] is less than key in [j]
     */
    fun less(i: Int, j: Int): Boolean {
        return comparator.compare(pq[i], pq[j]) < 0
    }

    /**
     * exchange
     * @param [i] [j]: Index of target keys.
     * Exchange keys in provided index.
     */
    fun exchange(i: Int, j: Int) {
        val temp = pq[i]
        pq[i] = pq[j]
        pq[j] = temp
    }

    /**
     * swim
     * @param [tarIdx] target Index where target key for reheapification is located.
     * function for reheapify.
     */
    fun swim(tarIdx: Int) {
        var k = tarIdx

        while (k > 1 && less(k / 2, k)) {
            exchange(k / 2, k)
            k = k / 2
        }
    }

    /**
     * sink
     * @param [tarIdx] target Index where target key for reheapification is located.
     * function for reheapify
     */
    fun sink(tarIdx: Int) {
        var k = tarIdx

        while (2 * k <= n) {
            var j = 2 * k
            // Test children first.
            // Reason for j < n condition: the less function test ouf j and J + 1.
            // Therefore, j must be less than n. (prevent IndexOutOfBoundsException)
            if (j < n && less(j, j + 1)) j++
            if (!less(k, j)) break

            // exchange
            exchange(k, j)
            k = j
        }
    }
}

// Quicksort for Transaction
class TransactionQuicksort(val comparator: Comparator<Transaction>) {

    /**
     * sort
     * Perform quicksort upon array of Transaction. It is recommended to shuffle the array first.
     * @param [a] Array of Transaction to be sorted.
     */
    fun sort(a: Array<Transaction>) {
        this.sort(a, 0, a.size - 1)
    }

    private fun sort(a: Array<Transaction>, lo: Int, hi: Int) {
        if (hi <= lo) return

        val j = partition(a, lo, hi)

        // Perform same operation on both left and right subarrays.
        sort(a, lo, j - 1)          // Left subarray.
        sort(a, j + 1, hi)          // Right subarray.
    }

    private fun partition(a: Array<Transaction>, lo: Int, hi: Int): Int {
        val pivot = a[lo]        // Choose the very first item as partitioning value (pivot value).
        // Necessary index.
        var i = lo
        var j = hi + 1

        // Main loop
        while(true) {
            // Starting from left, scan array until it finds key greater than pivot value.
            while(less(a[++i], pivot)) if (i == hi) break     // when i reaches hi, it means the scanning process completed.
            // Starting from right, scan array until it finds key smaller than pivot value.
            while(less(pivot, a[--j])) if (j == lo) break     // when j reaches lo, it means the scanning process completed.
            // Test out whether i and j have met or not.
            if (i >= j) break

            // At this point, i is an index of key that is greater than pivot, j is an index of key that is less than pivot.
            // Both keys should be exchanged, since keys less than pivot value should be on left half, greater than pivot should be on right half.
            exchange(a, i, j)
        }

        // Quicksort on this subarray has been completed. Now, the pivot value should be in fixed location,
        // which would be the right most of the left subarray.
        exchange(a, lo, j)

        return j
    }

    // necessary functions
    fun less(v: Transaction, w: Transaction): Boolean {
        return comparator.compare(v, w) < 0
    }

    fun exchange(a: Array<Transaction>, i: Int, j: Int) {
        val temp = a[i]
        a[i] = a[j]
        a[j] = temp
    }

    fun show(a: Array<Transaction>) {
        println("\n========== SORTED ==========")
        for (transaction in a) {
            println(transaction)
        }
    }

}

/**
 * Insertion Sort.
 */
class TransactionInsertionSort(val comparator: Comparator<Transaction>) {

    fun sort(a: Array<Transaction>) {
        // Traditional Approach.
        for (i in 0 until a.size) {
            for (j in i + 1 until a.size) {
                if (less(a[j], a[i])) exchange(a, i, j)
            }
        }
    }

    // Necessary Function
    fun less(v: Transaction, w: Transaction): Boolean {
        return comparator.compare(v, w) < 0
    }

    fun exchange(a: Array<Transaction>, i: Int, j: Int) {
        val temp = a[i]
        a[i] = a[j]
        a[j] = temp
    }

    fun show(a: Array<Transaction>) {
        println("\n========== SORTED ==========")
        for (transaction in a) {
            println(transaction)
        }
    }
}