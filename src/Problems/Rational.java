package Problems;

// Creative Problems (1.2.16)

public class Rational {
    private long numerator;
    private long denominator;

    /**
     * Create and store rational value.
     * @param numerator numerator
     * @param denominator non-zero denominator. (Zero-value denominator will cause exception.)
     */
    public Rational(int numerator, int denominator) {
        assert denominator != 0 : "Denominator cannot be 0";
        int commonFactor = gcd(numerator, denominator);
        if (commonFactor == 1) {
            this.numerator = numerator;
            this.denominator = denominator;
        } else {
            this.numerator = numerator / commonFactor;
            this.denominator = denominator / commonFactor;
        }
    }

    /**
     * Performs plus operation.
     * @param that@NotNull
     * @return Sum of this number and that.
     */
    public Rational plus(Rational that) {
        long newDenominator = lcm(this.denominator, that.denominator);
        long newNumerator = (this.numerator * newDenominator / this.denominator)
                + (that.numerator * newDenominator / that.denominator);
        return new Rational((int) newNumerator, (int) newDenominator);
    }

    /**
     * Performs minus operation.
     * @param that@NotNull
     * @return Difference of this number and that.
     */
    public Rational minus(Rational that) {
        long newDenominator = lcm(this.denominator, that.denominator);
        long newNumerator = (this.numerator * newDenominator / this.denominator)
                - (that.numerator * newDenominator / that.denominator);
        return new Rational((int) newNumerator, (int) newDenominator);
    }

    /**
     * Performs multiply operation.
     * @param that@NotNull
     * @return product of this number and that.
     */
    public Rational times(Rational that) {
        return new Rational((int) (this.numerator * that.numerator),
                (int) (this.denominator * that.denominator));
    }

    /**
     * Performs divide operation.
     * @param that@NotNull
     * @return Quotient of this number and that.
     */
    public Rational dividedBy(Rational that) {
        return new Rational((int) (this.numerator * that.denominator),
                (int) (this.denominator * that.numerator));
    }

    public boolean equals(Object that) {
        if (that == null) {
            return false;
        } else {
            if (this == that) return true;  // If both this and that refers same object, return true immediately.
            if (this.getClass() != that.getClass()) return false;

            Rational obj = (Rational) that;
            if (this.numerator != obj.numerator) return false;
            if (this.denominator != obj.denominator) return false;
        }
        return true;
    }

    public String toString() {
        return numerator +  " / " + denominator;
    }

    /**
     * Calculate greatest common divisor (GCD) using Euclid's Algorithm
     * @param p Non-zero int value.
     * @param q Non-zero int value.
     * @return Greatest common divisor of p and q.
     */
    private int gcd(int p, int q) {
        if (p < 1 && q < 1) throw new IllegalArgumentException("Both p and q are less than 1");
        if (q == 0) {
            return p;
        } else {
            int r = p % q;
            return gcd(q, r);
        }
    }

    /**
     * Calculate Least Common Factor
     * @param p Non-zero long value
     * @param q Non-zero long value
     * @return Least Common Factor of p and q.
     */
    private long lcm(long p, long q) {
        if (p < 1 || q < 1) throw new IllegalArgumentException("Either p or q is less than 1");
        return (p * q) / gcd((int) p, (int) q);
    }

    public static void main(String[] args) {
        int numerator1 = Integer.parseInt(args[0]);
        int denominator1 = Integer.parseInt(args[1]);
        int numerator2 = Integer.parseInt(args[2]);
        int denominator2 = Integer.parseInt(args[3]);

        Rational testInstance1 = new Rational(numerator1, denominator1);
        Rational testInstance2 = new Rational(numerator2, denominator2);
        Rational testInstance3 = new Rational(1,5);

        System.out.println("PLUS: " + testInstance1.plus(testInstance2));
        System.out.println("MINUS: " + testInstance1.minus(testInstance2));
        System.out.println("Multiply: " + testInstance1.times(testInstance2));
        System.out.println("Divide: " + testInstance1.dividedBy(testInstance2));

        System.out.println(testInstance1.equals(testInstance2));
        System.out.println(testInstance1.equals(testInstance3));
    }
}
