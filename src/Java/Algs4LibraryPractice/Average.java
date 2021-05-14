package Java.Algs4LibraryPractice;
// StdIn

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.InputMismatchException;

public class Average {
    public static void main(String[] args) {
        StdOut.println("Start Average.java");
        double sum = 0.0;
        int count = 0;

        while(!StdIn.isEmpty()) {
            try {
                sum += StdIn.readDouble();
                count += 1;
            } catch (InputMismatchException ime) {
                StdOut.println("Input Mismatched!");
                continue;
            }
        }

        StdOut.printf("Sum = %.2f Count = %d Average = %.2f\n", sum, count, (sum / count));
    }
}
