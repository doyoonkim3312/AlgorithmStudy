package Java.Algs4LibraryPractice.ADTImplementationExample;

// General Implementation of Date API. (ADT to encapsulate dates)

public class Date {
    private final int month;
    private final int day;
    private final int year;

    /**
     * Create a date.
     * @param month: Valid Month value (between 1 and 12)
     * @param day: Valid Day value (between 1 and 31)
     * @param year: Valid Year value (should be positive)
     */
    public Date(int month, int day, int year) {
        this.month = month;
        this.day = day;
        this.year = year;
    }

    public int month() {
        return month;
    }

    public int day() {
        return day;
    }

    public int year() {
        return year;
    }

    public String toString() {
        return month() + "/" + day() + "/" + year();
    }

    // Test Client
    public static void main(String[] args) {
        int month = Integer.parseInt(args[0]);
        int day = Integer.parseInt(args[1]);
        int year = Integer.parseInt(args[2]);

        Date date = new Date(month, day, year);
        System.out.println("General Implementation:\n" + date.toString());
    }
}
