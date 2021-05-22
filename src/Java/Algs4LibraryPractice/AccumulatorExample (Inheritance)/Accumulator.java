import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Iterator;

public class Accumulator {
	private int count;
	private double sum;

	// Default constructor will initialize both data-type values.

	public void addDataValue(double value) {
		sum += value;
		count++;
	}

	public double mean() {
		return sum / count;
	}

	public String toString() {
		return "Mean (" + count + " values): " + String.format("%7.5f", mean());

	}

	public int getCount() {
		return count;
	}

	public double getSum() {
		return sum;
	}
	
	// Test Client
	public static void main(String[] args) {
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		Iterator<String> inputIterator = input.lines().iterator();
		Accumulator accumulator = new Accumulator();

		while (inputIterator.hasNext()) {
			accumulator.addDataValue(Double.parseDouble(inputIterator.next()));
		}
		System.out.println(accumulator);
	}
}
