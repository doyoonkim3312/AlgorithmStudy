package coroutines

/*
    July 19, 2021 - Sequential, Concurrent
 */

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

fun main() {
    // sequentialByDefaultExample()
    simpleConcurrentExample()   // Faster than invoking two functions sequentially.
}

suspend fun doSomethingUsefulOne(): Int {
    delay(1000L)
    return 13
}

suspend fun doSomethingUsefulTwo(): Int {
    delay(1000L)
    return 29
}

fun sequentialByDefaultExample() = runBlocking {
    val time = measureTimeMillis {
        val first = doSomethingUsefulOne()
        val second = doSomethingUsefulTwo()
        println("Result: ${first + second}")
    }
    println("TASK COMPLETED in $time ms")
}

fun simpleConcurrentExample() = runBlocking {
    val time = measureTimeMillis {
        val first: Deferred<Int> = async {
            doSomethingUsefulOne()
        }
        val second: Deferred<Int> = async {
            doSomethingUsefulTwo()
        }
        println("Result: ${first.await() + second.await()}")
    }
    println("TASK COMPLETED in $time ms")
}