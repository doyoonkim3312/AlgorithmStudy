package Java.Algs4LibraryPractice;

import edu.princeton.cs.algs4.*;

public class GeometricObjectsExample {
    public static void main(String[] args) {
        double xmin = Double.parseDouble(args[0]);
        double xmax = Double.parseDouble(args[1]);
        double ymin = Double.parseDouble(args[2]);
        double ymax = Double.parseDouble(args[3]);
        int trials = Integer.parseInt(args[4]);


        // Interval1D: One-Dimensional interval
        Interval1D xInterval = new Interval1D(xmin, xmax);
        Interval1D yInternal = new Interval1D(ymin, ymax);

        // Interval2D: Two-Dimensional interval
        Interval2D box = new Interval2D(xInterval, yInternal);
        box.draw();

        int counter = 0;
        for (int i = 0; i < trials; i++) {
            double randX = StdRandom.uniform(0.0, 1.0);
            double randY = StdRandom.uniform(0.0, 1.0);
            Point2D randPoint = new Point2D(randX, randY);

            if (!box.contains(randPoint)) {
                randPoint.draw();
            } else {
                counter++;
            }
        }

        StdOut.println("Hits: " + counter);
        StdOut.println("Box Area: " + box.area());
    }
}
