package Problems;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Iterator;

public class RemoveDuplicates {
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

    public static int[] removeEqualValues(int[] array) {
        int[] temp = new int[array.length];
        temp[0] = array[0];
        int counter = 1;

        for (int i = 1; i < array.length; i++) {
            if (array[i] != array[i - 1]) {
                System.out.println("ARR: " + array[i]);
                temp[counter] = array[i];
                counter++;
            }
        }

        int[] modifiedList = new int[counter];
        int index = 0;
        for (int j = 0; j < counter; j++) {
            modifiedList[j] = temp[j];
        }
        return modifiedList;
    }

    // Development Client
    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int[] whitelist = allocateToArray(br.lines().iterator());

        // Using BinarySearch Algorithm, array must be sorted in Ascending order.
        Arrays.sort(whitelist);

        int[] modifiedWhiteList = removeEqualValues(whitelist);

        for (int element: whitelist) {
            System.out.println(element);
        }

        System.out.println("MODIFIED");
        for (int element: modifiedWhiteList) {
            System.out.println(element);
        }

    }
}
