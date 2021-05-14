package Java.Algs4LibraryPractice;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomSequence {
    public static void main(String[] args) {
        randomSequencePrint(Double.parseDouble(args[0]), Double.parseDouble(args[1]), Integer.parseInt(args[2]));
    }

    public static void randomSequencePrint(double lo, double hi, int len) {
        for (int i = 0; i < len; i++) {
            StdOut.printf("%.2f\n", StdRandom.uniform(lo, hi));
        }
    }
}
