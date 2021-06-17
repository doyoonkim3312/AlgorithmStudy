package AlgorithmAnalysis;

// Equation of log-log plot:
//  T(n) = an^3, where a = 9.98 * 10^-11

import java.util.Random;

public class DoublingTest {
    public static double TimeTrial(int n) {
        int MAX = 1000000;
        Random random = new Random();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = random.nextInt(MAX + MAX) - MAX;
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

    //Test Client
    public static void main(String[] args) {
        int upperBound = Integer.parseInt(args[0]);
        for (int n = 250; n < upperBound; n *= 2) {
            double time = TimeTrial(n);
            System.out.println("N: " + n + " TIME: " + time);
        }
    }
}
