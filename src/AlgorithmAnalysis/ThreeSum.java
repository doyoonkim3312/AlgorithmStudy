package AlgorithmAnalysis;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

// Test Result: Found 70 cases, Task Completed in 0.22s

public class ThreeSum {
    public static void main(String[] args) throws FileNotFoundException {
        //StopWatch sw = new StopWatch();
        long start = System.currentTimeMillis();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int[] arr = readInts(br.lines().iterator());
        int count = count(arr);
        long end = System.currentTimeMillis();
        System.out.println("TASK COMPLETED in " + (end - start) / 1000.0 + "s");
    }

    private static int[] readInts(Iterator<String> iterator) throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        while (iterator.hasNext()) {
            sb.append(iterator.next().replaceAll(" ", "")).append(" ");
        }
        String[] temp =  sb.toString().split(" ");
        int[] arr = new int[temp.length];
        for (int i = 0; i < temp.length; i++) {
            arr[i] = Integer.parseInt(temp[i]);
        }
        return arr;
    }

    private static int count(int[] a) {
        int n = a.length;
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                for (int k = j+1; k < n; k++) {
                    if (a[i] + a[j] + a[k] == 0) {
			    System.out.println("Case: " + a[i] + " " + a[j] + " " + a[k]);
			    count++;
		    }
                }
            }
        }
        return count;
    }
}
