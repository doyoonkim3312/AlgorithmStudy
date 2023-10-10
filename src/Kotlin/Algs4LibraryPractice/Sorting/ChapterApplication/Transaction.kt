package Kotlin.Algs4LibraryPractice.Sorting.ChapterApplication

import Kotlin.Algs4LibraryPractice.Sorting.ChapterApplication.Date.Companion.parseDate
import java.io.File
import java.util.*
import kotlin.Comparator
import kotlin.random.Random

// Example of General Implementation of ADT (Exercise 1.2.13)
class Transaction {
    private var customer: String
    private val date: Date
    private var amount: Double
    private var where: String

    // For sorting
    // By implementing Comparator interface, it is possible to create multiple compare function based on its needs.
    companion object {
        class WhoOrder : Comparator<Transaction> {
            override fun compare(o1: Transaction?, o2: Transaction?): Int {
                return o1!!.customer.compareTo(o2!!.customer)
            }

        }

        class WhenOrder: Comparator<Transaction> {
            override fun compare(o1: Transaction?, o2: Transaction?): Int {
                // Note: java$util$Date has been de-facto deprecated. Use LocalDate instead.
                return o1!!.date.toLocalDate().compareTo(o2!!.date.toLocalDate())
            }

        }

        class HowMuchOrder: Comparator<Transaction> {
            override fun compare(o1: Transaction?, o2: Transaction?): Int {
                if (o1!!.amount < o2!!.amount) return -1
                if (o1!!.amount > o2!!.amount) return +1
                return 0
            }

        }

        class WhereOrder: Comparator<Transaction> {
            override fun compare(o1: Transaction?, o2: Transaction?): Int {
                return o1!!.where.compareTo(o2!!.where)
            }

        }
    }

    /**
     * Default Constructor.
     * @param customer Name of customer.
     * @param date Date of transaction
     * @param amount Transaction Amount.
     */
    constructor(customer: String, where: String, date: Date, amount: Double) {
        this.customer = customer
        this.date = Date(date.month(), date.day(), date.year())
        this.amount = amount
        this.where = where
    }

    /**
     * Parsing Constructor.
     * @param stringFormatted Formatted String type information. Elements have to be separated by whitespace. (Example:
     * Customer MM/dd/YYYY amount)
     */
    constructor(stringFormatted: String) {
        val temp = stringFormatted.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        customer = temp[0]
        date = parseDate(temp[1])
        amount = temp[2].toDouble()
        where = temp[3]
    }

    fun customer(): String {
        return customer
    }

    fun date(): String {
        return date.toString()
    }

    fun amount(): Double {
        return amount
    }

    override fun equals(obj: Any?): Boolean {
        if (obj == null) {
            return false
        } else {
            if (this === obj) return true
            if (this.javaClass != obj.javaClass) return false
            val that = obj as Transaction
            if (customer != that.customer) return false
            if (!date.equals(that.date)) return false
            if (amount != that.amount) return false
        }
        return true
    }

    override fun toString(): String {
        return "$customer $where $date $amount"
    }
}

// Test client
fun main() {
    val scanner = Scanner(File("src/Kotlin/Algs4LibraryPractice/Sorting/ChapterApplication/sampleTransactions.txt"))
    val testSample = mutableListOf<Transaction>()

    while (scanner.hasNext()) {
        val data = scanner.nextLine().split(" ")
        testSample.add(
            Transaction(customer = data[0], where = data[1], date = Date.parseDate(data[2]), amount = Random.nextInt(1000).toDouble())
        )
    }

    for (element in testSample) {
        println(element)
    }

    scanner.close()
}