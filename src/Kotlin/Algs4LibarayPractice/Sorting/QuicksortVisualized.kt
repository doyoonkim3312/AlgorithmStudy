package Kotlin.Algs4LibarayPractice.Sorting

import edu.princeton.cs.algs4.StdDraw
import edu.princeton.cs.algs4.StdRandom
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun main() {

    val randomDouble = MutableList<Double>(10) {
        StdRandom.uniform() * 100
    }

//    val randomDouble = mutableListOf(
//        1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 2.0, 2.0, 2.0, 3.0, 3.0, 3.0, 3.0, 3.0, 4.0, 4.0, 4.0, 4.0, 5.0, 5.0, 5.0, 5.0, 5.0
//    )

//    val randomInt = mutableListOf(
//        0, 1, 2, 3, 4
//    )


    QuicksortVisualized<Double>().run {
        this.initializeDraw(randomDouble.size.toDouble())
        this.sort(randomDouble)
//        this.show(randomInt)
    }


//    Quicksort3wayVisualized<Double>().run {
//        this.initializeDraw(randomDouble.size.toDouble())
//        this.sort(randomDouble)
//    }
}

class QuicksortVisualized<T: Comparable<T>> : BasicSortOperations<T>() {
    override fun sort(a: MutableList<T>) {
        // Shuffle the given array in order to prevent the worst-case scenario (Quadratic Time Complexity)
        a.shuffle()

        // Call private function sort recursively.
        CoroutineScope(Dispatchers.Default).launch {
            draw(a, 0, a.size - 1)
            sort(a, 0, a.size - 1)
        }
    }

    private suspend fun sort(a: MutableList<T>, lo: Int, hi: Int) {
        if (hi <= lo) return
        // Basic Quicksort approach.
        val j = partition(a, lo, hi)

        // Current status: a[lo..j-1] < j < a[j+1..hi]
        // partitioning element J would stays the same position once it's declared and evaluated.
        sort(a, lo, j - 1)
        sort(a, j + 1, hi)
    }

    // Partitioning Function.
    private suspend fun partition(a: MutableList<T>, lo: Int, hi: Int): Int {
        // Indexing Variables.
        var i = lo
        var j = hi + 1

        // Partitioning Element (Or, Pivot element) (choose the very first element of the given array a.
        val v = a[lo]

        // Start scanning.
        while (true) {
            // Scan and evaluate the left subarray.
            while (less(a[++i], v)) if (i >= hi) break
            // Scan and evaluate the right subarray.
            while (less(v, a[--j])) if (j <= lo) break
            if (j <= i) break
            else {
                exchange(a, i, j)
                draw(a, i ,j)
            }
        }
        exchange(a, lo, j)
        draw(a, i, j)

        return j
    }

    fun initializeDraw(size: Double) {
        StdDraw.setXscale(-5.0, size + 5.0)
        StdDraw.setYscale(0.0, 100.0)
        StdDraw.setPenColor(StdDraw.GRAY)
    }

    private suspend fun draw(a: MutableList<T>, target1: Int, target2: Int) {
        delay(500L)

        // Clear out current drawing
        StdDraw.clear()
        for (index in a.indices) {
            if (index == target1) {
                StdDraw.setPenColor(StdDraw.RED)
            } else if (index == target2) {
                StdDraw.setPenColor(StdDraw.BLUE)
            } else {
                StdDraw.setPenColor(StdDraw.GRAY)
            }
            StdDraw.filledRectangle(index.toDouble() + 0.5, (a[index] as Double) / 2, 0.5, (a[index] as Double) / 2)
        }

    }

}

class Quicksort3wayVisualized<T: Comparable<T>> : BasicSortOperations<T>() {
    override fun sort(a: MutableList<T>) {
        // Shuffle given array in order to prevent the worst-case scenario.
        a.shuffle()

        // Call private sort function
        CoroutineScope(Dispatchers.Default).launch {
            draw(a, 0, a.size - 1)
            sort(a, 0, a.size - 1)
        }
    }

    private suspend fun sort(a: MutableList<T>, lo: Int, hi: Int) {
        if (hi <= lo) return

        // Indexing Variables.
        var lt = lo
        var i = lo + 1
        var gt = hi

        // Set partitioning value. (choose the very first item)
        val v = a[lo]

        // Start scanning.
        while (i <= gt) {
            // Directly use compareTo function provided by Comparable interface.
            val cmp = a[i].compareTo(v)

            if (cmp < 0) {              // Case 1: a[i] is less than v.
                val temp = Pair(lt++, i++)
                exchange(a, temp.first, temp.second)
                draw(a, temp.first, temp.second)
            } else if (cmp > 0) {       // Case 2: a[i] is greater than v.
                val temp = Pair(i, gt--)
                exchange(a, temp.first, temp.second)
                draw(a, temp.first, temp.second)
            } else {                    // Case 3: a[i] is equal to v.
                val temp = i++
                draw(a, temp, -1)
            }
        }

        sort(a, lo, lt - 1)
        sort(a, gt + 1, hi)

    }

    fun initializeDraw(size: Double) {
        StdDraw.setXscale(-5.0, size + 5.0)
        StdDraw.setYscale(0.0, 10.0)
        StdDraw.setPenColor(StdDraw.GRAY)
    }

    private suspend fun draw(a: MutableList<T>, target1: Int, target2: Int) {
        delay(500L)
        StdDraw.clear()
        for (index in a.indices) {
            if (index == target1) {
                StdDraw.setPenColor(StdDraw.RED)
            } else if (index == target2) {
                StdDraw.setPenColor(StdDraw.BLUE)
            } else  {
                StdDraw.setPenColor(StdDraw.GRAY)
            }
            StdDraw.filledRectangle(index.toDouble() + 0.5, (a[index] as Double) / 2, 0.5, (a[index] as Double) / 2)
        }
    }

}