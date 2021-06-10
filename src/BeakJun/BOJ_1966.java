package BeakJun;

/*
BOJ_1966: Printer Queue
 */

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class BOJ_1966 {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        Queue<PrintItem> printQueue;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testCount = Integer.parseInt(br.readLine());

        while (testCount > 0) {
            printQueue = new LinkedList<>();
            String[] line1 = br.readLine().split(" ");  // 0: Number of Items, 1: Target item index.
            String[] line2 = br.readLine().split(" ");  // list of items' priorities.
            // Queue Initialization
            for (int i = 0; i < Integer.parseInt(line1[0]); i++) {
                printQueue.add(new PrintItem(i, Integer.parseInt(line2[i])));
            }

            Arrays.sort(line2, Collections.reverseOrder());
            int printCount = 0;
            while (printCount < line2.length) {
                PrintItem item = printQueue.poll();
                if (item.priority == Integer.parseInt(line2[printCount])) {
                    printCount++;
                    if (item.initialQueueIndex == Integer.parseInt(line1[1])) {
                        break;
                    }
                } else {
                    printQueue.add(item);
                }
            }
            System.out.println(printCount);
            testCount--;
        }
        long end = System.currentTimeMillis();
        System.out.println("TASK COMPLETED in " + (end - start) / 1000.0 + "s.");
    }
}

class PrintItem {
    int initialQueueIndex;
    int priority;

    public PrintItem(int index, int priority) {
        this.initialQueueIndex = index;
        this.priority = priority;
    }
}