package Kotlin.BinarySearch

// Development Client
fun main() {
    var whitelist: List<Int> = listOf(84, 48, 68, 10, 18, 98, 12, 23, 54, 57, 33, 16, 77, 11, 29)
    var targetList: List<Int> = listOf(23, 50, 10, 99, 18, 23, 98, 84, 11, 10, 48, 77, 13, 54, 98, 77, 77, 68)

    var sortedWhiteList = whitelist.sorted()

    for (element in targetList) {
        if (indexOf(sortedWhiteList, element) == -1) {
            println(element)
        }
    }

    println("Binary Search Algorithm using recursive.")
    for (element in targetList) {
        if (indexOf(sortedWhiteList, 0, sortedWhiteList.size - 1, element) == -1) {
            println(element)
        }
    }

}

fun indexOf(array: List<Int>, key: Int) : Int {
    var lo = 0
    var hi = array.size - 1

    while (lo <= hi) {
        var mid = (lo + hi) / 2
        if (key < array[mid]) {
            hi = mid - 1;
        } else if (key > array[mid]) {
            lo = mid + 1;
        } else {
            return mid;
        }
    }
    return -1
}

fun indexOf(array: List<Int>, lo: Int, hi: Int, key: Int): Int {
    if (lo > hi) {
        return -1;
    } else {
        var mid = (lo + hi) / 2
        if (key < array[mid]) {
            // hi = mid - 1
            return indexOf(array, lo, mid - 1, key)
        } else if (key > array[mid]) {
            // lo = mid + 1
            return indexOf(array, mid + 1, hi, key)
        } else {
            return mid
        }
    }
}