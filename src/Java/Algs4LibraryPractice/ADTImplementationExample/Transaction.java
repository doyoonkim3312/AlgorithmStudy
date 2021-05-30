package Java.Algs4LibraryPractice.ADTImplementationExample;

// Example of General Implementation of ADT (Exercise 1.2.13)

public class Transaction {
    private String customer;
    private final Date date;
    private double amount;

    /**
     * Default Constructor.
     * @param customer Name of customer.
     * @param date Date of transaction
     * @param amount Transaction Amount.
     */
    public Transaction(String customer, Date date, double amount) {
        this.customer = customer;
        this.date = new Date(date.month(), date.day(), date.year());
        this.amount = amount;
    }

    /**
     * Parsing Constructor.
     * @param stringFormatted Formatted String type information. Elements have to be separated by whitespace. (Example:
     *                        Customer MM/dd/YYYY amount)
     */
    public Transaction(String stringFormatted) {
        String[] temp = stringFormatted.split(" ");
        this.customer = temp[0];
        this.date = Date.parseDate(temp[1]);
        this.amount = Double.parseDouble(temp[2]);
    }

    public String customer() { return customer; }

    public String date() { return date.toString(); }

    public double amount() { return amount; }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else {
            if (this == obj) return true;
            if (this.getClass() != obj.getClass()) return false;

            Transaction that = (Transaction) obj;
            if (!this.customer.equals(that.customer)) return false;
            if (!this.date.equals(that.date)) return false;
            if (this.amount != that.amount) return false;
        }
        return true;
    }

    public String toString() {
        return customer + " " + date.toString() + " " + amount;
    }

}
