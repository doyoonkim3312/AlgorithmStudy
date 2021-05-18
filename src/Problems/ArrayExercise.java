package Problems;

public class ArrayExercise {
    public static boolean[][] booleansList(int size) {
        boolean[][] list = new boolean[size][size];
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (gcd(row, col) == 1 && (row != 0 && col != 0)) {
                    list[row][col] = true;
                } else {
                    list[row][col] = false;
                }
            }
        }
        return list;
    }

    // Euclidean GCD Algorithm
    public static int gcd(int p, int q) {
        if (q == 0) {
            return p;
        } else {
            int r = p % q;
            return gcd(q, r);
        }
    }

    public static void main(String[] args) {
        boolean[][] testList = booleansList(10);
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                System.out.print(testList[row][col] + "\t");
            }
            System.out.println("\n");
        }
    }
}
