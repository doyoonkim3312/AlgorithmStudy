package Java.BagQueueStack.NodeExample;

// Example Client code in p.125

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Iterator;

public class Stats {
    public static void main(String[] args) {
        Bag<Double> bag = new Bag<>();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Iterator<String> inputIterator = br.lines().iterator();
        while (inputIterator.hasNext()) {
            bag.add(Double.parseDouble(inputIterator.next()));
        }

        double sum = 0.0;
        while (bag.hasNext()) {
            sum += bag.next();
        }
        double mean = sum / bag.size();

        sum = 0.0;
        while (bag.hasNext()) {
            sum += Math.pow((bag.next() - mean), 2);
        }
        double stddev = Math.sqrt(sum);

        System.out.println("MEAN: " + mean);
        System.out.println("STDDEV: " + stddev);
    }
}
