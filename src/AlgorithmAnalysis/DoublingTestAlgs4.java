package AlgorithmAnalysis;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class DoublingTestAlgs4 {
    public static double timeTrial(int n) {
        int MAX = 1000000;
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = StdRandom.uniform(-MAX, MAX);
        }
        long start = System.currentTimeMillis();
        int count = count(arr);
        long end = System.currentTimeMillis();
        return (end - start) / 1000.0;
    }

    public static int count(int[] a) {
        int n = a.length;
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                for (int k = j+1; k < n; k++) {
                    if (a[i] + a[j] + a[k] == 0) count++;
                }
            }
        }
        return count;
    }

    public static void main(String[] args) {
        int upperBound = Integer.parseInt(args[0]);
        for (int i = 250; i < upperBound; i *= 2) {
            double time = timeTrial(i);
            StdOut.printf("%7d %7.1f\n", i, time);
        }
    }
}
