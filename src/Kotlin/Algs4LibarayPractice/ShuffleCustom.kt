package Kotlin

import edu.princeton.cs.algs4.StdRandom

fun main() {
    var testArray: MutableList<Double> = mutableListOf(1.0, 2.0, 3.0, 4.0, 5.0)
    shuffle(testArray)
    println("Using custom shuffle() function")
    for (element: Double in testArray) {
        println(element)
    }

    testArray.shuffle()
    println("Using exists shuffle() function")
    for (element: Double in testArray) {
        println(element)
    }
}

fun shuffle(a: MutableList<Double>) {
    var n = a.size
    for (i in a.indices) {
        var r = i + StdRandom.uniform(n - i)
        var temp: Double = a[i];
        a[i] = a[r]
        a[r] = temp
    }
}