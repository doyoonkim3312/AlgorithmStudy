package BeakJun;

import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class BOJ_10845 {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        Queue<Integer> queue = new LinkedList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int ioCount = Integer.parseInt(br.readLine());

        for (int i = 0; i < ioCount; i++) {
            String[] option = br.readLine().split(" ");
            switch (option[0].toLowerCase()) {
                case "push": {
                    if (option[1] != null) queue.add(Integer.parseInt(option[1]));
                    break;
                }
                case "pop": {
                    Integer item = queue.poll();
                    if (item == null) System.out.println(-1);
                    else System.out.println(item);
                    break;
                }
                case "size": {
                    System.out.println(queue.size());
                    break;
                }
                case "empty": {
                    if (queue.isEmpty()) System.out.println(1);
                    else System.out.println(0);
                    break;
                }
                case "front": {
                    Integer item = queue.peek();
                    if (item == null) System.out.println(-1);
                    else System.out.println(item);
                    break;
                }
                case "back": {
                    Iterator<Integer> queueIterator = queue.iterator();
                    Integer item = -1;
                    while(queueIterator.hasNext()) {
                        item = queueIterator.next();
                        if (!queueIterator.hasNext()) {
                            break;
                        }
                    }
                    System.out.println(item);
                }
            }
        }

        long end = System.currentTimeMillis();
        System.out.println("Time Taken: " + (end - start) / 1000.0 + "s");
    }
}
