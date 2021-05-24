package Java.BinarySearch;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Scanner;

public class ClientExample {
    public static void main(String[] args) throws FileNotFoundException {
        BufferedReader whiteListInput = new BufferedReader(new InputStreamReader(new FileInputStream(args[0])));
        Iterator<String> whiteListInputIterator = whiteListInput.lines().iterator();
        StaticSetOfInt set = new StaticSetOfInt(allocateToArray(whiteListInputIterator));

        BufferedReader targetListInput = new BufferedReader(new InputStreamReader(System.in));
        Iterator<String> targetIterator = targetListInput.lines().iterator();
        while (targetIterator.hasNext()) {
            int key = Integer.parseInt(targetIterator.next());
            if (!set.contains(key)) {
                System.out.println(key);
            }
        }

    }

    public static int[] allocateToArray(Iterator<String> iterator) {
        StringBuilder sb = new StringBuilder();
        while (iterator.hasNext()) {
            sb.append(iterator.next()).append(" ");
        }

        String[] temp = sb.toString().split(" ");
        int[] returnArray = new int[temp.length];

        for (int i = 0; i < temp.length; i++) {
            returnArray[i] = Integer.parseInt(temp[i]);
        }
        return returnArray;
    }
}
