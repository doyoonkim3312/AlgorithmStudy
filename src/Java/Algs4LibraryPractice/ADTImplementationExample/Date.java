package Java.Algs4LibraryPractice.ADTImplementationExample;

// General Implementation of Date API. (ADT to encapsulate dates)

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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


    /**
     * Parse String value from user input and allocate each component to proper argument value.
     * @param dateString Formatted String type Date value. (Example: MM/dd/YY)
     * @return new Date object.
     */
    public static Date parseDate(String dateString) {
        String[] temp = dateString.split("/");
        return new Date(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]), Integer.parseInt(temp[2]));
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

    // Overridden equals() method.
    /*
        Equality: Java's convention is that equals() must be an equivalence relation. It must be:
        " Take an Object as an argument.
        1. Reflexive: x.equals(y) is true.
        2. Symmetric: x.equals(y) is true, if and only if y.equals(x) is true.
        3. Transitive: x.equals(y) and y.equals(z) are true, then so is x.equals(z).
        4. Consistent: Multiple invocations of x.equals(y) consistently return the same value, provided neither object
        is modified.
        5. Not Null
     */

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            // Null Checking
            return false;
        } else {
            // 1. Compare Reference Values.
            if (this == obj) return true;

            // 2. Compare Class Value using getClass() method. getClass() method ensures same reference value for
            // all objects in any given class
            if (this.getClass() != obj.getClass()) {
                return false;
            }

            // 3. Case argument to Date. (This casting procedure has to be succeed because of Step 2.
            Date target = (Date) obj;

            // 4. Compare each instance variables.
            if (this.day != target.day) return false;
            if (this.month != target.month) return false;
            if (this.year != target.year) return false;
        }
        return true;
    }

    // Test Client
    public static void main(String[] args) throws IOException {
        /*
        int month = Integer.parseInt(args[0]);
        int day = Integer.parseInt(args[1]);
        int year = Integer.parseInt(args[2]);
         Date date = new Date(month, day, year);
         */
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String userInput = br.readLine();

        Date date = Date.parseDate(userInput);
        Date test1 = new Date(5, 26, 2021);
        Date date2 = date;
        System.out.println("General Implementation:\n" + date.toString());

        System.out.println(date == test1);
        System.out.println(date == date2);
        System.out.println(date2 == test1);

        System.out.println("EQUALS METHOD");
        System.out.println(date.equals(test1));
        System.out.println(date.equals(date2));
        System.out.println(date2.equals(test1));
    }
}
