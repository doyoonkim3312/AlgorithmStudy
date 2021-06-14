package BeakJun;

// BOJ_11660

import java.util.*;
import java.io.*;

public class BOJ_11660 {
    public static void main(String[] args) throws IOException {
        /*
        long start = System.currentTimeMillis();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] input1 = br.readLine().split(" ");  // 0: N, 1: count
        final int N = Integer.parseInt(input1[0]);
        int count = Integer.parseInt(input1[1]);

        int[][] matrix = new int[N][N];

        try {
            for (int row = 0; row < matrix.length; row++) {
                String[] line = br.readLine().split(" ");
                for (int i = 0; i < matrix.length; i++) {
                    matrix[row][i] = Integer.parseInt(line[i]);
                }
            }
        } catch (IndexOutOfBoundsException iobe) {
            System.out.println(Arrays.toString(iobe.getStackTrace()));
        }

        int[] from, to;
        int grandTotal = 0;
        while (count > 0) {
            String[] input2 = br.readLine().split(" ");
            from = new int[]{Integer.parseInt(input2[0]), Integer.parseInt(input2[1])};
            to = new int[]{Integer.parseInt(input2[2]), Integer.parseInt(input2[3])};

            for (int row = from[0] -1; row <= to[0] - 1; row++) {
                for (int col = from[1] -1; col <= to[1] -1; col++) {
                    grandTotal += matrix[row][col];
                }
            }
            System.out.println(grandTotal);
            grandTotal = 0;
            count--;
        }
        long end = System.currentTimeMillis();
        System.out.println("TASK COMPLETED in " + (end - start) / 1000.0 + "s");
         */
        fixed();
    }

    public static void fixed() throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] input1 = br.readLine().split(" ");  // 0: N, 1: count
        int N = Integer.parseInt(input1[0]);
        int count = Integer.parseInt(input1[1]);
        StringBuilder sb = new StringBuilder();

        while (N > 0) {
            if (N == 1) sb.append(br.readLine());
            else sb.append(br.readLine()).append('\n');
            N--;
        }
        String matrix = sb.toString();

        int[] from, to;
        int grandTotal = 0;
        while (count > 0) {
            String[] input2 = br.readLine().split(" ");
            from = new int[]{Integer.parseInt(input2[0]), Integer.parseInt(input2[1])};
            to = new int[]{Integer.parseInt(input2[2]), Integer.parseInt(input2[3])};

            for (int row = from[0] -1; row <= to[0] - 1; row++) {
                String[] temp = matrix.split("\n")[row].split(" ");
                for (int col = from[1] -1; col <= to[1] -1; col++) {
                    grandTotal += Integer.parseInt(temp[col]);
                }
            }
            System.out.println(grandTotal);
            grandTotal = 0;
            count--;
        }
        long end = System.currentTimeMillis();
        System.out.println("TASK COMPLETED in " + (end - start) / 1000.0 + "s");
    }
}
