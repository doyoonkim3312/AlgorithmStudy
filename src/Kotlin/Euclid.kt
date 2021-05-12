package Kotlin

fun main(args: Array<String>) {
    println("KOTLIN")
    var result: Int = gcd(1111111, 1234567)
    println("Result: $result")
}

fun gcd(p: Int, q: Int): Int {
    if (q == 0) {
        return p
    } else {
        var r: Int = p % q
        println("New Argument: p = $q q = $r")
        return gcd(q, r)
    }
}