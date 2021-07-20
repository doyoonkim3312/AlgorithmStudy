package coroutines

/*
    July 19, 2021 - Sequential, Concurrent
    July 20, 2021 - lazily started, async-style function, structured concurrency
 */

import kotlinx.coroutines.*
import kotlin.system.*

fun main() {
    // sequentialByDefaultExample()
    // simpleConcurrentExample()   // Faster than invoking two functions sequentially.
    // lazilyStartedAsync()
    structuredConcurrencyExample()
}

suspend fun doSomethingUsefulOne(): Int {
    delay(1000L)
    return 13
}

suspend fun doSomethingUsefulTwo(): Int {
    delay(1000L)
    return 29
}

// Structured Concurrency
suspend fun concurrentSum(): Int = coroutineScope {
    val first = async {
        doSomethingUsefulOne()
    }
    val second = async {
        doSomethingUsefulTwo()
    }
    first.await() + second.await()
}

/*
The code below is strongly discouraged in Kotlin Coroutine.

fun doSomethingUsefulOneAsync() = GlobalScope.async {
    doSomethingUsefulOne()
}

fun doSomethingUsefulTwoAsync() = GlobalScope.async {
    doSomethingUsefulTwo()
}

fun asyncStyleFunction() {
    val time = measureTimeMillis {
        val first = doSomethingUsefulOneAsync()
        val second = doSomethingUsefulTwoAsync()

        // Main function dose not have runBlocking, however, waiting for a result must involve either
        // suspend function or blocking.
        runBlocking {
            println("Result: ${first.await() + second.await()}")
        }
    }
    println("TASK COMPLETED in $time ms")
}
 */

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

fun lazilyStartedAsync() = runBlocking {
    val time = measureTimeMillis {
        // Each coroutines will be started if .start() function is invoked, or its deferred value is required by
        // .await() function.

        val first = async (start = CoroutineStart.LAZY) {
            // println("DO SOMETHING USEFUL ONE")
            doSomethingUsefulOne()
        }
        val second = async (start = CoroutineStart.LAZY) {
            // println("DO SOMETHING USEFUL TWO")
            doSomethingUsefulTwo()
        }
        first.start()   // this line starts coroutine, and return deferred value to value 'first'
        second.start()
        println("Result: ${first.await() + second.await()}")
    }
    println("TASK COMPLETED in $time ms")
}

fun structuredConcurrencyExample() = runBlocking {
    val time = measureTimeMillis {
        println("Result: ${concurrentSum()}")
    }
    println("TASK COMPLETED in $time ms")
}