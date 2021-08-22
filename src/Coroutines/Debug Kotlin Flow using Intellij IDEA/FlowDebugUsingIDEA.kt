package FlowDebugWithIDEA

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

/*
    Kotlin Flow Debug using Intellij IDEA
 */

fun main() = runBlocking {
    /*
    simple().collect { response ->
        delay(300L)     // Give a 300ms of computation time before collecting the result.
        println("Response: $response.")
    }
     */

    // Add a Concurrently running coroutines.
    simple()
        .buffer()   // buffer() function will store emitted value and runs the flow collector in separate coroutine.
        .collect { response ->
            delay(300L)
            println("Response: $response.")     // No.2 Breakpoint at the line where println() function is called
                                                // (where collector is called.)
        }

}

fun simple() = flow<Int> {
    for (number in 1..3) {
        delay(100L)     // Give a 100ms of computation time before emitting the result.
        emit(number)        // Debug the coroutine (No.1 Breakpoint at the line where emit() function is called.)
    }
}
