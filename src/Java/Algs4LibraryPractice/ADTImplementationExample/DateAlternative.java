package Java.Algs4LibraryPractice.ADTImplementationExample;

// Alternative Implementation method of Date. (ADT to encapsulate dates.)
// Compare to general implementation, this method is considered as a "Space-efficiency at the cost of more time."
// "LESS SPACE, MORE TIME (MORE CALCULATION)"

public class DateAlternative {
    private final int value;

    public DateAlternative(int month, int day, int year) {
        value = year * 512 + month * 32 + day;
    }

    public int month() {
        return (value / 32) % 16;
    }

    public int day() {
        return value % 32;
    }

    public int year() {
        return value / 512;
    }

    public String toString() {
        return month() + "/" + day() + "/" + year();
    }

    // Test Client
    public static void main(String[] args) {
        int month = Integer.parseInt(args[0]);
        int day = Integer.parseInt(args[1]);
        int year = Integer.parseInt(args[2]);

        DateAlternative dateAlternative = new DateAlternative(month, day, year);
        System.out.println("Alternative Implementation:\n" + dateAlternative.toString());
    }
}
