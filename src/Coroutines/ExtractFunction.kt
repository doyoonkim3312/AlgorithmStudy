package Coroutines

import kotlinx.coroutines.*

fun main() {
    runBlocking {
        launch {
            doWorld()
        }
        println("Hello")
    }
    println("First Coroutine Completed")

    runBlocking {
        doKotlin()
    }
    println("Second Coroutine Completed")
}

// Suspending Function.
suspend fun doWorld(): Unit {
    delay(1000L)
    println("World")
}

suspend fun doKotlin(): Unit {
    // It is possible to declare custom scope using 'coroutineScope' builder.
    // It is also possible to perform multiple concurrent operation.
    coroutineScope {
        launch {
            delay(2000L)
            println("Kotlin!")
        }
        launch {
            delay(4000L)
            println("Kotlin!!")
        }
        println("Hello")
    }
}