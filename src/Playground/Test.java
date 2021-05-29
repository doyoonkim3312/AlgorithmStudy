package Playground;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Test {
    private int[] testArr;

    public Test(int[] arr) {
        testArr = Arrays.copyOf(arr, arr.length);
    }

    public void append(int number) {
        // Program will be terminated if condition in assert statement returns false.
        assert number >= 0 : "Negative Number is not allowed.";
        int[] temp = Arrays.copyOf(testArr, testArr.length + 1);
        temp[temp.length - 1] = number;
        testArr = temp;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int element: testArr) {
            sb.append(element).append(" ");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        int[] arr = {1,2,3,4,5};
        Test a = new Test(arr);
        a.append(10);
        System.out.println(a);
        a.append(-50);
        System.out.println(a);
    }
}
