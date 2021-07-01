package Coroutines

import kotlinx.coroutines.*
import kotlin.concurrent.thread

fun main() {
    lightWeightDemo()
    //lightWeightDemoCompare()
}

fun explicitJob() = runBlocking {
    val job: Job = launch {
        delay(1000L)
        println("Kotlin!")
    }
    println("Hello")
    job.join()
    println("Coroutines")
}

fun lightWeightDemo() = runBlocking {
    repeat(100_000) {
        val job: Job = launch {
            delay(5000L)
            println("COUNT: $it")
        }
        job.join()
    }
}

fun lightWeightDemoCompare() {
    // Using thread causes OutOfMemoryError.
    repeat(100_000) {
        thread {
            Thread.sleep(5000L)
            println("COUNT: $it")
        }
    }
}