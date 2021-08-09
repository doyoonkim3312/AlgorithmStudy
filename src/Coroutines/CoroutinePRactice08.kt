package coroutines

// Coroutine Exception Handling

import kotlinx.coroutines.*

fun main() {
    exceptionHandlingUnderRootCoroutine()
}

// Example of Exception Handling under ROOT coroutine.
fun exceptionHandlingUnderRootCoroutine() = runBlocking<Unit> {
    val job = GlobalScope.launch {  // Root Coroutine created by launch builder.
        println("This Root Coroutine will throw an IndexOutOfBoundsException.")
        throw IndexOutOfBoundsException()   // Will be printed in console by Thread.defaultUncaughtExceptionHandler)
    }
    job.join()
    println("Failed to execute $job.")

    val deferredValue = GlobalScope.async { // Root Coroutine created by async builder.
        println("This Root Coroutine will throw an ArithmeticException.")
        throw ArithmeticException() // Won't be printed until user consume the result of this coroutine. (wait until
                                    // user calls .await() or .awaitAll() )
    }

    // User consume deferredValue
    try {
        deferredValue.await()   // Exception will be caught and handled by try-catch statement.
        println("This Line will not be reached.")
    } catch (e: ArithmeticException) {
        println("Caught Exception $e while executing deferredValue.")
    }
}

