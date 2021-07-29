package coroutines

/*
    Asynchronous Flow
 */

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun main() {
    // simpleSequenceType().forEach { number -> println(number) }
    // runBlocking<Unit> { simpleSuspendingFunctionType().forEach{ number -> println(number)}}
    // flowTypeExample()
    // coldStreamExample()
    // flowCancellationExample()
    // flowBuilderExample()
    intermediateOperators()
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
fun simpleReturnsFlow(): Flow<Int> {
    // code inside of flow {...} is suspendable, which means, suspend modifier is no longer needed.
    return flow {
        println("Flow Started.")
        for (i in 1..3) {
            delay(100L)
            println("Emitting $i")
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
    simpleReturnsFlow().collect { number -> println(number) }
}

// Flow is a COLD STREAM. Code inside of Flow Builder will be executed when flow is collected.
fun coldStreamExample() = runBlocking<Unit> {
    println("Calling Simple Function that returns Flow")
    val flow: Flow<Int> = simpleReturnsFlow()   // The code inside of Flow Builder does not run since the FLow is not
                                                // collected yet.
    println("Collect Flow")
    flow.collect { number -> println(number) }
    println("Collect Flow Again")
    flow.collect { number -> println(number) }
}

// Flow Cancellation follows General cooperative cancellation of coroutines.
fun flowCancellationExample() = runBlocking<Unit> {
    withTimeoutOrNull(250L) {
        simpleReturnsFlow().collect { number -> println(number) }
    }
    println("Code Completed due to timeouts.")
}

fun flowBuilderExample() = runBlocking<Unit> {
    val basicFlowBuilder: Flow<Int> = flow {
        for (i in 1..5) {
            emit(i)
        }
    }

    val flowWithFixedValues: Flow<Int> = flowOf(1,2,3,4,5)

    val convertIntoFlow: Flow<Int> = listOf(1,2,3,4,5).asFlow()

    println("Basic Flow Builder")
    basicFlowBuilder.collect { value -> println(value) }

    println("Flow with Fixed Set of Values")
    flowWithFixedValues.collect { value -> println(value) }

    println("Convert Collection into Flow")
    convertIntoFlow.collect{ value -> println(value) }
}

// Intermediate Operators of Flow
suspend fun processRequest(request: Int): String {
    delay(1000L)    // Pretend as a long-running computation.
    return "Response: $request"
}

fun intermediateOperators() = runBlocking {
    (1..3).asFlow()
        .map { value -> processRequest(value) } // Code inside of intermediate operator can call suspending function.
        .collect { response -> println(response) }
}
