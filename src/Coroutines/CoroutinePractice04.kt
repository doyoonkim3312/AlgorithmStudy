package coroutines

/*
    July 21, 2021 - CoroutineContext (Dispatcher)
 */

import kotlinx.coroutines.*

val newSingleThread = newSingleThreadContext("MyOwnThread")

fun main() {
    // dispatchersExample()
    unconfinedVsConfined()
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