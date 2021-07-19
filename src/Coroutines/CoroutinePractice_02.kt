package coroutines

/*
    July 18, 2021 - Cancelling Coroutines.
    July 19, 2021 - Timeout
 */

import kotlinx.coroutines.*

fun main() {
    /*
    runBlocking {
        // Example code for concurrency Test
        val start = System.currentTimeMillis()
        println("Start Testing")
        concurrencyExample()
        println("End Testing")
        val end = System.currentTimeMillis()
        println("TASK COMPLETED in ${(end - start) / 1000}s")
    }
     */
    // cancellationExample()
    // cancellableComputation()
    // closingResourcesWithFinally()
    // nonCancellableBlock()
    // timeoutExample()
    // timeoutOrNullExample()
    var cnt = 0
    while (cnt < 10) {
        asynchronousTimeoutExample()
        cnt++
    }
}

fun cancellationExample() = runBlocking {
    val job = launch {
        repeat(1000) {
            println("job: I'm sleeping $it ...")
            delay(500L)
        }
    }
    delay(1300L)
    println("main: I'm tired of waiting!")
    job.cancel()
    job.join()
    println("main: Now I can quit.")
}

fun cancellableComputation() = runBlocking {
    val startTime = System.currentTimeMillis()
    val job = launch(Dispatchers.Default) {
        var nextPrintTime = startTime
        var i = 0
        /*
            while (i < 5) {
            yield() // Make computation cancellable using suspend function 'yield(),' which checks for cancellation
            if (System.currentTimeMillis() >= nextPrintTime) {
                println("job: I'm sleeping ${i++} ...")
                nextPrintTime += 500L
                }
            }
         */
        while (isActive) {  // Explicitly check the cancellation status using 'isActive' value.
            if (System.currentTimeMillis() >= nextPrintTime) {
                println("job: I'm sleeping ${i++} ...")
                nextPrintTime += 500L
            }
        }
    }
    delay(1300L)
    println("main: I'm tired of waiting!")
    job.cancelAndJoin()
    println("main: Now I can quit.")
}

fun closingResourcesWithFinally() = runBlocking {
    val job = launch {
        try {
            repeat(1000) {
                println("job: I'm sleeping $it ...")
                delay(500L)
            }
        } catch (exception: CancellationException) {
            // this code block can be replaced with finally {...}, and it is recommended way.
            println("job: Coroutine is cancelled")
        }
    }
    delay(1300L)
    println("main: I'm tired of waiting!")
    job.cancelAndJoin()
    println("main: Now I can quit.")
}

fun nonCancellableBlock() = runBlocking {
    val job = launch {
        try {
            repeat(1000) {
                println("job: I'm sleeping $it ...")
                delay(500L)
            }
        } finally {
            withContext(NonCancellable) {
                // suspend function can be invoked in cancelled coroutine inside of withContext function with
                // NonCancellable context. Without wrapping suspend function using 'withContext', suspend function
                // (in this case, delay()) will never be executed, since it causes CancellationException
                println("job: Executing code in 'finally' block")
                delay(1000L)
                println("job: Waiting 1s because coroutine is cancelled.")
            }
        }
    }
    delay(1300L)
    println("I'm tired of waiting!")
    job.cancelAndJoin()
    println("Now I can quit.")
}

fun timeoutExample() = runBlocking {
    try {
        withTimeout(1300L) {
            repeat(1000) {
                println("job: I'm sleeping $it ...")
                delay(500L)
            }
        }
    } catch(e: TimeoutCancellationException) {  // TimeoutCancellationException is a child class of CancellationException.
        println("job: This coroutine will be cancelled after 1s delay")
        withContext (NonCancellable) {
            delay(1000L)
            println("job: Cancelled.")
        }
    }
    println("main: Running coroutine has been cancelled.")
}

fun timeoutOrNullExample() = runBlocking {
    val result = withTimeoutOrNull(1300L) {
        repeat(3) {
            println("job: I'm sleeping $it ...")
            delay(500L)
        }
        println("job: Completed.")  // Job will be cancelled before it completes its task and produce this result.
    }
    println("main: Result is $result")
}

// Asynchronous Timeout and Resources
var acquired = 0

class Resource {
    init {
        acquired++
    }
    fun close() {
        acquired--
    }
}

fun asynchronousTimeoutExample() {
    runBlocking {
        repeat(100_000) {
            launch {
                var resource: Resource? = null
                try {
                    withTimeout(60) {
                        delay(50)
                        resource = Resource()
                    }
                } finally {
                    resource?.close()
                }
            }
        }
    }
    println(acquired)
}
//

suspend fun concurrencyExample() = coroutineScope {
    val response = mutableListOf<Deferred<Int>>()
    repeat(100) {
        val computation: Deferred<Int> = async (Dispatchers.Default) {
            println("Start Computation: $it out of 100")
            delay(3000L)
            println("Complete Computation: $it out of 100")
            it + 100
        }
        response.add(computation)
    }
    response.awaitAll()
}