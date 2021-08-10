package coroutines

// Coroutine Exception Handling

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.flow.*

fun main() {
    // exceptionHandlingUnderRootCoroutine()
    // coroutineExceptionHandlerExample()
    // cancellationExceptionIgnoredByHandler()
    exceptionHandlingUnderParentChildRelationship()
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

// CoroutineExceptionHandler.
// On JVM, it is possible to redefine global exception handler for all coroutines by registering
// CoroutineExceptionHandler via ServiceLoader.
// CoroutineExceptionHandler is invoked only on uncaught exceptions - exceptions that were not handled in any other way.
fun coroutineExceptionHandlerExample() = runBlocking<Unit> {
    // Create new CoroutineExceptionHandler.
    // CoroutineExceptionHandler { CoroutineContext, Throwable -> ... }
    val exceptionHandler = CoroutineExceptionHandler { _, e ->
        println("Coroutine Exception Handler got $e exception.")
    }

    // Root Coroutine (using launch builder)
    val rootCoroutineReturnsJob = GlobalScope.launch(exceptionHandler) {
        throw AssertionError()
    }

    // Root Coroutine (using async builder)
    val rootCoroutineReturnsDeferred = GlobalScope.async(exceptionHandler) {
        throw ArithmeticException() // Noting will be printed until user consume this coroutine.
    }

    joinAll(rootCoroutineReturnsJob, rootCoroutineReturnsDeferred)
}

// Cancellation and Exception.
// Coroutine internally use CancellationException for cancellation, these exception are ignored by handlers, so they
// should be used only as the source of additional debug information, which can be obtained by catch block.
fun cancellationExceptionIgnoredByHandler() = runBlocking<Unit> {
    // In this example, CoroutineExceptionHandler is always installed to a coroutine that is created in GlobalScope.
    val parent = launch {
        val child = launch {
            try {
                delay(Long.MAX_VALUE)
            } finally {
                println("This Child coroutine has been cancelled.")
            }
        }
        yield() // Review: yield() - Periodically invoke suspend functions, that checks for cancellation.
                // Child coroutine will start its extremely long computation.
        println("Cancelling Child Coroutine.")
        child.cancel()  // Internally using CancellationException, which is going to be ignored by handler.
        yield()
        println("Parent Coroutine is not cancelled.")   // If child coroutine encounters an exception other than
                                                        // CancellationException, it cancels its parent coroutine
                                                        // with that exception.
    }
    parent.join()
}

// Cancellation and Exception (2)
fun exceptionHandlingUnderParentChildRelationship() = runBlocking {
    val exceptionHandler = CoroutineExceptionHandler { _, e ->
        println("Exception Handler got $e exception.")
    }

    val parent = GlobalScope.launch(exceptionHandler) {
        // Launch its first child coroutine.
        launch {
            try {
                delay(Long.MAX_VALUE)
            } finally {
                withContext(NonCancellable) {
                    println("First Child coroutine has been cancelled, but exception is not handled until " +
                            "all the children coroutines terminate")
                    delay(1000L)
                    println("The first Child coroutine completes its non cancellable block.")
                }
            }
        }

        // launch its second child coroutine.
        launch {
            delay(10L)
            println("Second Child coroutine will throw ArithmeticException.")
            throw ArithmeticException() // Exception will be handled until all the children coroutines terminate.
        }
    }
    parent.join()
}
