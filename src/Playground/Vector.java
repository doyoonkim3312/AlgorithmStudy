package Playground;

// General Example of actually not implementing an immutable type

public class Vector {
    private final double[] coord;

    public Vector(double[] a) {
        this.coord = a; // Both coord and a refer same memory location.
    }

    public String toString() {
        return coord[0] + " " + coord[1];
    }

    // Test Client
    public static void main(String[] args) {
        double[] a = {3.0, 4.0};
        Vector vector = new Vector(a);
        System.out.println(vector);
        a[0] = 10.0;    // Bypass the API
        System.out.println(vector);
    }
}
