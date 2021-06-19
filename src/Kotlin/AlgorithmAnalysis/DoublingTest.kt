package Kotlin.AlgorithmAnalysis

import java.util.Random

// Doubling Test.

class DoublingTest {

    fun timeTrial(n: Int): Double {
        val start: Long = System.currentTimeMillis();
        val MAX: Int = 1000000
        val random: Random = Random()
        val arr: Array<Int> = Array<Int>(n) {i: Int ->
            random.nextInt(MAX + MAX) - MAX
        }
        val count = count(arr)
        val end: Long = System.currentTimeMillis()
        return (end - start) / 1000.0
    }

    fun count(array: Array<Int>): Int {
        val n = array.size - 1
        var count: Int = 0
        for (i: Int in 0..n) {
            for (j: Int in i+1..n) {
                for (k: Int in j+1..n) {
                    if (array[i] + array[j] + array[k] == 0) count++;
                }
            }
        }
        return count
    }
}

fun main(args: Array<String>) {
    val upperBounds: Int = 16000
    var i = 250
    while (i <= upperBounds) {
        var time = DoublingTest().timeTrial(i)
        println("N: $i TIME: $time")
        i *= 2
    }
}