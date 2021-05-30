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
            try {
                bag.add(Double.parseDouble(inputIterator.next()));
            } catch (NumberFormatException nfe) {
                System.out.println("Empty String detected");
                break;
            } catch (NullPointerException npe) {
                System.out.println("Null Value detected");
                break;
            }
        }

        double sum = 0.0;
        for (Double value: bag) {
            sum += value;
        }
        double mean = sum / bag.size();

        sum = 0.0;
        for (Double value: bag) {
            sum += Math.pow((value - mean), 2);
        }
        double stddev = Math.sqrt(sum / (bag.size() - 1));

        System.out.println("MEAN: " + mean);
        System.out.println("STDDEV: " + stddev);
    }
}
