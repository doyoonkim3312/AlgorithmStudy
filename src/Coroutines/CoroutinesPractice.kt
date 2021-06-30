package Coroutines

import kotlinx.coroutines.*

fun main()  {
    runBlocking {
        // Separate coroutines from main function
        launch {
            // first Coroutine
            delay(100L)
            println("First Coroutine Completed")
        }
        launch {
            // second Coroutine
            delay(300L)
            println("Second Coroutine Completed")
        }
    }
    println("Exit CoroutineScope")
}