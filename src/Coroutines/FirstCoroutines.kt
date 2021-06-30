package Coroutines

import kotlinx.coroutines.*


fun main() = runBlocking { // this: CoroutineScope
    launch { // launch a new coroutine and continue.
        delay(1000L) // non-blocking delay for 1 second (default time unit: ms)
        println("World!") // print after delay
    }
    println("Hello") // main coroutine continues while a previous one is delayed
}

/*
fun main() {
    runBlocking {
        launch {
            delay(1000L)
            println("World!")
        }
    }
    println("Hello")
}
 */
