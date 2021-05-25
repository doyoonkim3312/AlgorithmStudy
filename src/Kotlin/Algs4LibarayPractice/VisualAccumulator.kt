package Kotlin.Algs4LibarayPractice

import edu.princeton.cs.algs4.StdDraw
import edu.princeton.cs.algs4.StdRandom

class VisualAccumulator(private var max: Double, private var trial: Int) {
    private var sum = 0.0
    private var count = 0
    init {
        StdDraw.setXscale(0.0, trial.toDouble())
        StdDraw.setYscale(0.0, max)
        StdDraw.setPenRadius(0.005)
    }

    fun addDataValue(value: Double) {
        sum += value
        count++;

        StdDraw.setPenColor(StdDraw.DARK_GRAY);
        StdDraw.point(count.toDouble(), value);
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.point(count.toDouble(), mean());
    }

    fun mean(): Double {
        return sum / count;
    }

    override fun toString(): String {
        return "Mean $count values: " + String.format("%7.5f", mean());
    }
}

fun main(args: Array<String>) {
    var trials: Int = 2000
    var visualAccumulator = VisualAccumulator(1.0, trials)

    for (i: Int in 1..trials) {
        visualAccumulator.addDataValue(StdRandom.uniform());
    }

    println(visualAccumulator);
}