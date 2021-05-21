package Java.Algs4LibraryPractice.Accumulator;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.Iterator;

public class VisualAccumulator {
    private int count;
    private double sum;

    public VisualAccumulator(int totalNumbers, double max) {
        StdDraw.setXscale(0, totalNumbers);
        StdDraw.setYscale(0.0, max);
        StdDraw.setPenRadius(0.005);
    }

    public void addDateValue(double value) {
        sum += value;
        count++;
        StdDraw.setPenColor(StdDraw.DARK_GRAY);
        StdDraw.point(count, value);
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.point(count, mean());
    }

    public double mean() {
        return sum / count;
    }

    public String toString() {
        return "Mean (" + count + " values): " + String.format("%7.5f", mean());
    }

    // TestClient
    public static void main(String[] args) {
        int trial = Integer.parseInt(args[0]);
        VisualAccumulator visualAccumulator = new VisualAccumulator(trial, 1.0);

        for (int i = 0; i < trial; i++) {
            visualAccumulator.addDateValue(StdRandom.uniform(0.0, 1.0));
        }

        System.out.println(visualAccumulator);
    }
}
