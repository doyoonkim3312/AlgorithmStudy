package Problems;

import java.io.*;
import java.util.Iterator;
import java.util.Locale;
import java.util.Scanner;

public class BinarySearchProblem {
    public static int indexOf(int[] array, int lo, int hi, int key) {
        if (lo > hi) {
            return -1;
        } else {
            int mid = (lo + hi) / 2;
            System.out.println("MID: " + mid);
            if (key > array[mid]) {
                System.out.println("Boundaries: L:" + (mid + 1) + " R: " + hi);
                return indexOf(array, mid + 1, hi, key);
            } else if (key < array[mid]) {
                System.out.println("Boundaries: L:" + lo + " R: " + (hi - 1));
                return indexOf(array, mid, mid -1, key);
            } else {
                // key == array[mid]
                System.out.println("Index Found: " + mid);
                return mid;
            }
        }
    }

    public static int lowBoundary(int[] array, int lo, int hi, int key) {
        if (lo < hi) {
            int mid = (lo + hi) / 2;
            if (key > array[mid]) {
                return lowBoundary(array, mid + 1,  hi, key);
            } else {
                // If array[mid] == key, Right boundary has to be decrease in order to find lower boundary.
                return lowBoundary(array, lo, mid - 1, key);
            }
        }
        return lo;
    }

    public static int upperBoundary(int[] array, int lo, int hi, int key) {
        if (lo < hi) {
            int mid = (lo + hi)  / 2;
            if (key < array[mid]) {
                return upperBoundary(array, lo, mid - 1, key);
            } else {
                // If array[mid] == key, Left boundary has to be increased in order to find upper boundary.
                return upperBoundary(array, mid + 1, hi, key);
            }
        }
        return hi;
    }


    public static int rank(int[] array, int key) {
        int index = indexOf(array, 0, array.length -1, key);
        return index + 1;
    }

    public static void count(int[] array, int key) {
        System.out.println("Lower Boundary: " + lowBoundary(array, 0, array.length - 1, key));
        System.out.println("Upper Boundary: " + upperBoundary(array, 0, array.length - 1, key));
    }

    public static int[] allocateToArray(Iterator<String> iterator) {
        StringBuilder sb = new StringBuilder();
        while (iterator.hasNext()) {
            sb.append(iterator.next()).append(" ");
        }
        String[] temp = sb.toString().split(" ");
        int[] returnedValue = new int[temp.length];

        for (int index = 0; index < temp.length; index++) {
            returnedValue[index] = Integer.parseInt(temp[index]);
        }
        return returnedValue;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //System.out.println(br.lines().count());
        Iterator<String> iterator = br.lines().iterator();

        int[] whitespace = allocateToArray(iterator);
        System.out.println(rank(whitespace, 80));
        count(whitespace, 80);

    }
}
