package coroutines

/*
    Asynchronous Flow
 */

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun main() {
    // simpleSequenceType().forEach { number -> println(number) }
    // runBlocking<Unit> { simpleSuspendingFunctionType().forEach{ number -> println(number)}}
    flowTypeExample()
}

// Sequence; Result of computing the numbers with come CPU-consuming blocking code.
fun simpleSequenceType(): Sequence<Int> {
    return sequence {
        for (i in 1..3) {
            Thread.sleep(100)   // Illustrates time taking computation. (Blocking The thread; In this case, Main
                                     // Main Thread.
            yield(i)    // yield Next Value.
        }
    }
}

// Suspending Function; Example using sequence above actually blocks Main Thread that is running the code. Suspending
// function can be treated as a alternative of this situation. Suspending function will compute values asynchronously,
// but not blocking Main Thread.
suspend fun simpleSuspendingFunctionType(): List<Int> {
    delay(100L)
    return listOf(1,2,3)
}

// Suspending Function above successfully compute values asynchronously without blocking Main Thread. However, it returns
// only list of values, not stream of values (like Sequence.) (Note: Sequence cannot be used in the example above, because
// the sequence builder used above creates new scope that is not inherited from CoroutineScope.)
// The solution is the Flow<T>. (similar to Sequence type.)
suspend fun simpleSuspendingFunctionReturnsFlow(): Flow<Int> {
    return flow {
        for (i in 1..3) {
            delay(100L)
            emit(i)
        }
    }
}

fun flowTypeExample() = runBlocking {
    launch {
        // Create concurrent coroutine to check whether Main Thread is blocked or not.
        for (i in 1..30) {
            println("I'm not blocked $i")
            delay(10L)
        }
    }
    simpleSuspendingFunctionReturnsFlow().collect { number -> println(number) }
}