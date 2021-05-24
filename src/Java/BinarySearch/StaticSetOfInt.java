package Java.BinarySearch;

import java.util.Arrays;
import java.util.Iterator;

public class StaticSetOfInt {
    private final int[] array;

    /**
     * Default constructor takes array of Int as a argument, and using it to initialize array type value after sorting
     * in ascending order.
     * @param array
     */
    public StaticSetOfInt(int[] array) {
        this.array = Arrays.copyOf(array, array.length);
        Arrays.sort(this.array);
    }

    /**
     * Contains mehtod takes int-type key value and determine whether array contains passed key value.
     * @param key
     * @return true, if array contains key value, false, if array does not contain key value.
     */
    public boolean contains(int key) {
        return indexOf(key) != -1;
    }

    private int indexOf(int key) {
        // BinarySearch Algorithm
        int lo = 0;
        int hi = array.length - 1;

        // Using while statement rather than recursion
        while (lo <= hi) {
            int mid = (lo + hi) / 2;
            if (key < mid) {
                hi = mid - 1;
            } else if (key > mid) {
                lo = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }
}
