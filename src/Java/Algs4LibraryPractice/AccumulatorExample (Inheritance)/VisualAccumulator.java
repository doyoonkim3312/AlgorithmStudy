import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

public class VisualAccumulator extends Accumulator {
	// Constructor
	public VisualAccumulator(int trials, double max) {
		super();
		StdDraw.setXscale(0, trials);
		StdDraw.setYscale(0, max);
		StdDraw.setPenRadius(0.005);
	}

	public void addDataValue(double value) {
		super.addDataValue(value);
		StdDraw.setPenColor(StdDraw.DARK_GRAY);
		StdDraw.point(getCount(), value);
		StdDraw.setPenColor(StdDraw.RED);
		StdDraw.point(getCount(), mean());
	}

	// Test Client
	public static void main(String[] args) {
		int trials = Integer.parseInt(args[0]);
		VisualAccumulator visualAccumulator = new VisualAccumulator(trials,1.0);
		
		for (int i = 0; i < trials; i++) {
			visualAccumulator.addDataValue(StdRandom.uniform(0.0, 1.0));
		}
		System.out.println(visualAccumulator);
	}
}
