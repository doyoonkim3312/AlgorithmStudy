package Kotlin.Algs4LibarayPractice

import edu.princeton.cs.algs4.StdRandom

class Accumulator(private var sum: Double, private var count: Int) {

    fun addDataValue(value: Double) {
        sum += value;
        count++;
    }

    fun mean(): Double {
        return sum / count;
    }

    override fun toString(): String {
        return "Mean $count values: " + String.format("%7.5f", mean());
    }
}

// Test Client
fun main(args: Array<String>) {
    //var trials = args[0].toInt();
    val trials = 10;
    var accumulator = Accumulator(0.0, 0);

    for (i: Int in 0..trials) {
        accumulator.addDataValue(StdRandom.uniform());
    }
    println(accumulator);
}