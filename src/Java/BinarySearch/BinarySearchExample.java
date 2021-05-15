package Java.BinarySearch;

// Simple BinarySearch Algorithm implementation.

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BinarySearchExample {
    public static int indexOf(int[] whitelist, int key) {
        // Return Index value of key value. (BinarySearch Algorithm)
        int lo = 0; // Starting Index (lo)
        int hi = whitelist.length - 1;  // Starting Index (hi)

        while (lo <= hi) {
            int mid = (lo + hi) / 2;
            if (key < whitelist[mid]) {
                hi = mid - 1;   // Keep odd values of searching array.
            } else if (key > whitelist[mid]) {
                lo = mid + 1;   // Keep odd values of searching array.
            } else {
                return mid; // Return index of target value.
            }
        }
        return -1;
    }

    public static int indexOf(int[] whitelist, int lo, int hi, int key) {
        // Same BinarySearch Method, but using recursive.
        if (lo >= hi) {
            return  -1;
        } else {
            int mid = (lo + hi) / 2;
            if (key < whitelist[mid]) {
                hi = mid - 1;
                return indexOf(whitelist, lo, hi, key);
            } else if (key > whitelist[mid]) {
                lo = mid + 1;
                return indexOf(whitelist, lo, hi, key);
            } else {
                return mid; //Return index of target value.
            }
        }
    }

    // Development Client
    public static void main(String[] args) {
        StdOut.println("Start Development Client");
        In input = new In(args[0]);
        int[] whitelist = input.readAllInts();

        // Using BinarySearch Algorithm, array must be sorted in Ascending order.
        Arrays.sort(whitelist);

        while(!StdIn.isEmpty()) {
            int target = StdIn.readInt();
            if (indexOf(whitelist, target) == -1) {
                // If target is not in whitelist, print target.
                StdOut.println(target);
            }
        }
    }
}
