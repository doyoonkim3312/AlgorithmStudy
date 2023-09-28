package Kotlin.Algs4LibarayPractice.Sorting

import edu.princeton.cs.algs4.StdDraw
import edu.princeton.cs.algs4.StdRandom
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.awt.Color


fun main() {
    val randomDouble = mutableListOf<Double>()

    for (i in 0..15) {
        val rand = (StdRandom.uniform() * 100)
        randomDouble.add(rand)
    }

    val visualized = InsertionVisualized(randomDouble.size)
    visualized.draw(randomDouble)
    visualized.sort(randomDouble)
}

class InsertionVisualized(
    val size: Int
) : SortOperations() {

    init {
        StdDraw.setXscale(-5.0, size.toDouble() + 5.0)
        StdDraw.setYscale(0.0, 100.0)
        StdDraw.setPenColor(StdDraw.GRAY)
    }

    override fun <T : Comparable<T>> sort(a: MutableList<T>) {
        CoroutineScope(Dispatchers.Default).launch {
            for (i in 1 until a.size) {
                var currentTarget = i
                draw(a, currentTarget)
                for (j in i downTo 1) {
                    delay(500L)
                    if (less(a[j], a[j - 1])) {
                        exchange(a, j, j - 1)
                        currentTarget = j - 1
                        draw(a, currentTarget)
                    }
                }
            }
        }
    }

    fun <T: Comparable<T>> draw(a: MutableList<T>, target: Int = 0) {
        StdDraw.clear()
        for (index in a.indices) {
            // Consider the data is a Double type.
            if (index == target) {
                println("Index: $index, target: $target")
                StdDraw.setPenColor(StdDraw.RED)
            } else {
                StdDraw.setPenColor(StdDraw.GRAY)
            }
            StdDraw.filledRectangle(index.toDouble() + 0.5, (a[index] as Double) / 2, 0.5, (a[index] as Double) / 2)
        }
    }

}