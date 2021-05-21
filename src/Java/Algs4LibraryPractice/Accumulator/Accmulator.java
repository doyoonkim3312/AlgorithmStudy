package Java.Algs4LibraryPractice.Accumulator;

// Streaming Algorithm: An algorithm that processes its input values one at a time, using an amount of memory that is
// much less than the input size.

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Iterator;

public class Accmulator {
    private double sum;
    private int count;

    // Default constructor will initialize both data-type values to 0.0 and 0

    /**
     * Add a new data value.
     * @param value New Data value (double)
     */
    public void addDataValue(double value) {
        sum += value;
        count++;
    }

    /**
     * Calculate mean value of cumulative values.
     * @return mean
     */
    public double mean() {
        return sum / count;
    }

    /**
     * Overridden toString() method.
     * @return
     */
    public String toString() {
        return "Mean (" + count + " values): " + String.format("%7.5f", mean());
    }

    // Test Client
    public static void main(String[] args) {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        Iterator<String> inputIterator = input.lines().iterator();
        Accmulator accmulator = new Accmulator();

        while (inputIterator.hasNext()) {
            accmulator.addDataValue(Double.parseDouble(inputIterator.next()));
        }

        System.out.println(accmulator.toString());
    }
}
