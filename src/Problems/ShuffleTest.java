package Problems;

import edu.princeton.cs.algs4.StdRandom;

// Perform Empirical Test of StdRandom.shuffle(double[] a)
public class ShuffleTest {

    public static int[] initsList(int size) {
        int[] list = new int[size];
        for (int i = 0; i < list.length; i++) {
            list[i] = i;
        }
        return list;
    }

    public static void main(String[] args) {
        int m = Integer.parseInt(args[0]);
        int n = Integer.parseInt(args[1]);

        int[] testList = new int[m];
        int[][] resultTable = new int[m][m];

        while (n > 0) {
            testList = initsList(m);
            StdRandom.shuffle(testList);
            for (int j = 0; j < m; j++) {
                resultTable[testList[j]][j]++;
            }
            n--;
        }

        for (int row = 0; row < m; row++) {
            for (int col = 0; col < m; col++) {
                System.out.print(resultTable[row][col] + "\t");
            }
            System.out.print("\n");
        }
    }
}
