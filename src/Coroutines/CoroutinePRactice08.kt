package coroutines

// Coroutine Exception Handling

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.flow.*
import java.io.IOException

fun main() {
    // exceptionHandlingUnderRootCoroutine()
    // coroutineExceptionHandlerExample()
    // cancellationExceptionIgnoredByHandler()
    // exceptionHandlingUnderParentChildRelationship()
    // exceptionAggregationExample()
    // aggregationOfCancellationExceptionExample()
    // supervisorJobExample()
    // supervisorScopeExample()
    exceptionHandlingInSupervisorScope()
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

// Exception Aggregation: "The first exception wins"
fun exceptionAggregationExample() = runBlocking<Unit> {
    // Exception Handler
    val exceptionHandler = CoroutineExceptionHandler {_, e ->
        println("Exception Handler got $e exception, with suppressed ${e.suppressed!!.contentToString()}")
        // Parameter 'e' could be null, which possibly causes NPE, so Non-null asserted by !! operator is needed.
    }

    val parent = GlobalScope.launch(exceptionHandler) {
        // First Child Coroutine.
        launch {
            try {
                delay(Long.MAX_VALUE)   // Pretends this coroutine perform extremely complicated computation.
                                        // This coroutine will cancelled when Second Child Coroutine throws IOException.
            } finally {
                throw ArithmeticException() // Throw ArithmeticException.
                                            // This exception will be suppressed by IOException, which is thrown first.
            }
        }

        // Second Child Coroutine.
        launch {
            delay(100L)
            throw IOException() // Throw IOException (First Exception Thrown.)
        }

        delay(Long.MAX_VALUE)
    }
    parent.join()
}

// Exception Aggregation (2)
// Cancellation Exceptions are transparent and unwrapped by default.
fun aggregationOfCancellationExceptionExample() = runBlocking<Unit> {
    // Coroutine Exception Handler
    val exceptionHandler = CoroutineExceptionHandler {_, e ->
        println("Exception Handler got $e exception.")
    }

    val parent = GlobalScope.launch(exceptionHandler) {
        // Create Stack of Children coroutines.
        val inner = launch {
            launch {
                launch {
                    throw IOException() // The Original Exception.
                }
            }
        }
        try {
            inner.join()    // Join all the stack of children coroutines. (When inner is cancelled by Original Exception,
                            // all the stack of children coroutines will get cancelled.
        } catch (e: CancellationException) {
            println("Rethrowing CancellationException with original cause.")
            throw e // CancellationException will be rethrown, yet the original exception gets to the handler.
        }
    }
    parent.join()
}

// Supervision Job
// SupervisorJob is similar to a regular Job with the only exception that cancellation is propagated only downwards
// (Unidirectional)
fun supervisorJobExample() = runBlocking<Unit> {
    val supervisorJob = SupervisorJob()
    with(CoroutineScope(coroutineContext + supervisorJob)) {
        // Launch the first child coroutine. (Its exception will be ignored in this example.)
        val firstChildCoroutine = launch(CoroutineExceptionHandler {_, _ -> }) {
            println("The first child coroutine is failing.")
            throw AssertionError()
        }

        // Launch the second child coroutine.
        val secondChildCoroutine = launch {
            firstChildCoroutine.join()

            // Cancellation of the first child coroutine is not propagated to the second child coroutine.
            println("The first child coroutine is cancelled with ${firstChildCoroutine.isCancelled}," +
                    "but the second child coroutine is still alvie $isActive")
            
            try {
                delay(Long.MAX_VALUE)
            } finally {
                // But the cancellation of the supervisor is propagated.
                println("The second child coroutine is cancelled because the supervisor job is cancelled.")
            }
        }
        firstChildCoroutine.join()  // Wait until first child coroutine fails & completes.
        println("Cancelling the supervisor")
        supervisorJob.cancel()
        secondChildCoroutine.join()
    }
}

// Supervision Scope
// supervisorScope can be used for scoped concurrency. It propagates the cancellation in the one direction only and
// and cancels all its children only if it failed itself.
fun supervisorScopeExample() = runBlocking<Unit> {
    try {
        supervisorScope {
            // launch the first child coroutine.
            val child = launch {
                try {
                    println("This child is sleeping.")
                    delay(Long.MAX_VALUE)
                } finally {
                    println("The child is cancelled.")
                }
            }
            yield() // Give child a chance to execute.
            println("Throwing an exception from the scope.")
            throw AssertionError()  // supervisorScope will fail itself, and all its children coroutines will be cancelled.
        }
    } catch (e: AssertionError) {
        println("Caught an Assertion Error.")
    }
}


fun exceptionHandlingInSupervisorScope() = runBlocking {
    // Create new Coroutine Exception Handler
    val exceptionHandler = CoroutineExceptionHandler { _, e ->
        println("Coroutine Exception Handler got $e exception.")
    }

    supervisorScope {
        // Create child coroutine of SupervisorScope
        val child = launch(exceptionHandler) {  // Every child should handle its exception by itself via the exception
                                                // handling mechanism (Coroutine Exception Handler in this case.)
                                                // it's because child's failure does not propagate to the parents.
            println("The child throws an exception.")
            throw AssertionError()
        }
        println("The scope is completing.")
    }
    println("The scope is completed.")
}