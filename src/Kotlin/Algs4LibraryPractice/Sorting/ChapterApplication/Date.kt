package Kotlin.Algs4LibraryPractice.Sorting.ChapterApplication

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

// General Implementation of Date API. (ADT to encapsulate dates)
// Note: Java Date has been de-facto deprecated since Java 11.
fun main() {
    val test1 = Date(10, 10, 2023)
    val test2 = Date(10, 11, 2023)
    println(test1.toLocalDate())

    println("${test1.toLocalDate() == test2.toLocalDate()}")
}
class Date
/**
 * Create a date.
 * @param month: Valid Month value (between 1 and 12)
 * @param day: Valid Day value (between 1 and 31)
 * @param year: Valid Year value (should be positive)
 */(private val month: Int, private val day: Int, private val year: Int) {
    fun month(): Int {
        return month
    }

    fun day(): Int {
        return day
    }

    fun year(): Int {
        return year
    }

    fun toLocalDate(): LocalDate {
        return LocalDate.parse(this.toString(), DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.US))
    }

    override fun toString(): String {
        if (month() < 10) {
            return "0$month/$day/$year"
        } else {
            return "$month/$day/$year"
        }
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
    override fun equals(obj: Any?): Boolean {
        if (obj == null) {
            // Null Checking
            return false
        } else {
            // 1. Compare Reference Values.
            if (this === obj) return true

            // 2. Compare Class Value using getClass() method. getClass() method ensures same reference value for
            // all objects in any given class
            if (this.javaClass != obj.javaClass) {
                return false
            }

            // 3. Case argument to Date. (This casting procedure has to be succeed because of Step 2.
            val target = obj as Date

            // 4. Compare each instance variables.
            if (day != target.day) return false
            if (month != target.month) return false
            if (year != target.year) return false
        }
        return true
    }

    companion object {
        /**
         * Parse String value from user input and allocate each component to proper argument value.
         * @param dateString Formatted String type Date value. (Example: MM/dd/YY)
         * @return new Date object.
         */
        @JvmStatic
        fun parseDate(dateString: String): Date {
            val temp = dateString.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            return Date(temp[0].toInt(), temp[1].toInt(), temp[2].toInt())
        }
    }
}
