package coroutines

/*
    July 21, 2021 - CoroutineContext (Dispatcher)
 */

import kotlinx.coroutines.*

val newSingleThread = newSingleThreadContext("MyOwnThread")

fun log(msg: String) = println("[${Thread.currentThread()}] $msg")

fun main() {
    // dispatchersExample()
    // unconfinedVsConfined()
    // debugExample()
    // jumpBetweenThreads()
    // jobRetrievedExample()
    // jobOverriddenExample()
    // parentalResponsibilities()
    namingCoroutine()
}

// Dispatchers
fun dispatchersExample() = runBlocking {
    // 01. Using Dispatcher context that main runBlocking uses.
    launch {
        println("[main runBlocking]     I'm working in thread ${Thread.currentThread()}!")
    }

    // 02. Not confined; will work with main thread.
    launch (Dispatchers.Unconfined) {
        println("[Unconfined]           I'm working in thread ${Thread.currentThread()}!")
    }

    // 03. Default; will get thread from DefaultDispatcher (Shared Thread Pool on JVM)
    launch (Dispatchers.Default) {
        println("[Default]              I'm working in thread ${Thread.currentThread()}!")
    }

    // 04. Programmer-Defined Thread; will get its own new thread.
    @OptIn(kotlinx.coroutines.ObsoleteCoroutinesApi::class)
    launch (newSingleThread) {
        println("[New Single Thread]    I'm working in thread ${Thread.currentThread()}")
    }
    newSingleThread.close()     // Dedicated thread is very expensive resource. So, it must be released when no
                                // longer needed, or stored in top-level variable and reuse it throughout the
                                // Application.
}

fun unconfinedVsConfined() = runBlocking {
    // Unconfined
    launch (Dispatchers.Unconfined) {
        println("[Unconfined]           I'm working in thread ${Thread.currentThread()}!")
        delay(500L)
        println("[Unconfined]           I'm working in thread ${Thread.currentThread()}!")
    }

    // Context is inherited from parent CoroutineScope. (In this case, runBlocking)
    launch {
        println("[Main runBlocking]     I'm working in thread ${Thread.currentThread()}!")
        delay(1000L)
        println("[Main runBlocking]     I'm working in thread ${Thread.currentThread()}!")
    }
}

//Debugging output example
fun debugExample() = runBlocking {
    // Coroutine #1: runBlocking

    val a = async (Dispatchers.Unconfined) {
        // Coroutine #2: First async (Unconfined)
        log("I'm calculating a piece of answer.")
        delay(300L)
        log("I'm printing a piece of answer.")
        10
    }

    val b = async {
        // Coroutine #3: Second async (Confined to main)
        delay(400L)
        log("I'm printing another piece of answer.")
        15
    }
    log("The answer is ${a.await() + b.await()}")
}

fun jumpBetweenThreads() {
    // Dedicated thread is very expensive, so it must be properly handled.
    // In this example, dedicated thread is released by .use function when it is no longer needed.
    newSingleThreadContext("Ctx1").use { ctx1 ->
        newSingleThreadContext("Ctx2").use { ctx2 ->
            runBlocking (ctx1) {
                // Using runBlocking with explicitly specified context.
                log("Started in ctx1")
                withContext(ctx2) {
                    // Using withContext, which changes its context while staying in a same coroutine.
                    log("Working in ctx2")
                }
                log("Back to ctx1")
            }
        }
    }
}

fun jobRetrievedExample() = runBlocking {
    // Coroutine's Job is part of its context, and it can be retrieved.
    log("runBlocking: My Job is ${coroutineContext[Job]}")

    val a = launch {
        log("launch: My Job is ${coroutineContext[Job]}")
    }
    println("${a.isActive}")
    a.join()
    println("${a.isActive}")
}

// Job Overridden
fun jobOverriddenExample() = runBlocking {
    val request = launch {
        // New Job object is explicitly passed to child coroutine of request
        launch (Job()) {
            log("Job1: I'm child coroutine of request. I have my own Job to do")
            delay(1000L)
            log("Job1: Since I have my own Job, I'm not affected by my parent's cancellation")
        }

        // New child coroutine that inherits its Job from its parent.
        launch {
            log("Job2: I'm child coroutine of request. My Job is inherited from my parent's Job.")
            delay(1000L)
            log("Job2: Since my Job is inherited from my parent's Job, I'm affected by my parent's Job.")
        }
    }
    delay(500L)
    request.cancel()
    delay(1000L)
    println("main: Who is survived request cancellation?")
}

// Parental Responsibilities.
fun parentalResponsibilities() = runBlocking {
    // Launch a coroutine for incoming computation.
    val request = launch {
        repeat(3) {
            launch {
                delay((it + 1) * 200L)
                log("Coroutine $it completed.")
            }
        }
        log("request: I'm done, and I'm not going to explicitly join my child coroutines that are still active.")
    }
    request.join()
    log("main: Now processing of the request is completed.")
}

// Naming Coroutine.
fun namingCoroutine() = runBlocking(CoroutineName("Main Coroutine")) {
    // First Child Coroutine
    val a = async (CoroutineName("First Coroutine")) {
        delay(1000L)
        log("Computing First answer.")
        1600
    }

    // Second Child Coroutine
    val b = async (CoroutineName("Second Coroutine")) {
        delay(1500L)
        log("Computing Second answer.")
        40
    }
    log("The answer of first / second = ${a.await() / b.await()}")
}