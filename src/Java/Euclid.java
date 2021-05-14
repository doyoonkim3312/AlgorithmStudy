package Java;

import Java.Test;
import edu.princeton.cs.algs4.StdOut;

public class Euclid {
    public static void main(String[] args) {
        System.out.println("JAVA");
        int result = gcd(1111111, 1234567);
        System.out.println("Result: " + result);
        StdOut.print("Result: " + result);
    }

    public static int gcd(int p, int q) {
        if (q == 0) {
            return p;
        } else {
            int r = p % q;
            System.out.println("New Argument: p = " + q + " q = " + r);
            return gcd(q, r);
        }
    }
}
