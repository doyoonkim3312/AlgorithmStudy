package Java.Algs4LibraryPractice;
// Custom Shuffule Method Test.

import static edu.princeton.cs.algs4.StdRandom.uniform;

public class ShuffleCustom {
	public static void main(String[] args) {
		double[] testList = {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0};
		shuffle(testList);
		for (double element: testList) {
			System.out.println(element);
		}
	}

	public static void shuffle(double[] a) {
		int n = a.length;
		for (int i = 0; i < n; i++) {
			int r = i + uniform(n - i);
			System.out.println("i = " + i + " r = " + r);
			double temp = a[i];
			a[i] = a[r];
			a[r] = temp;
			System.out.println("Shuffled:\na[i] = " + a[i] + " a[r] = " + a[r]);
		}
	}
}
