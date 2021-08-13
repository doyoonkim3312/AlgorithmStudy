package coroutines

/*
    Shared Mutable state and Concurrency.
 */

import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.measureTimeMillis

fun main() {
    // possibleParallelismProblemExample()
    // threadSafeDataStructureExample()
    threadConfinementExample()
}

// Parallelism Problem in Kotlin Concurrency using Multi-Threading.
// Possible parallelism problem: synchronization of access to shared mutable state.
suspend fun massiveComputation(action: suspend() -> Unit) {
    val numOfCoroutines = 100   // Total number of coroutine would be launched.
    val numOfRepeatComputation = 1000   // Total number of repeated action.

    val startTime = measureTimeMillis {
        coroutineScope {
            repeat(numOfCoroutines) {
                launch {
                    repeat(numOfRepeatComputation) {
                        action()
                    }
                }
            }
        }
    }
    println("${numOfCoroutines * numOfRepeatComputation} TASK COMPLETED in ${System.currentTimeMillis() - startTime}s")
}

@Volatile   // @Volatile is a annotation in Kotlin which makes variable volatile. (Possible Solution for parallelism problem)
var counter = 0
// Volatile variable cannot be the solution for parallelism problem in Kotlin Concurrency, because volatile variables
// guarantee linearizable (technical term: "atomic") reads and writes to the corresponding variable, but do not provide
// atomically of larger actions (increment in this example.)


fun possibleParallelismProblemExample() = runBlocking {
    // launch 'massiveComputation,' which access to counter, shared mutable variable, and increase counter by 1.
    withContext(Dispatchers.Default) {
        massiveComputation {
            counter++
        }
    }
    println("Counter = $counter")   // It does not actually prints 100000, because a hundred coroutines increment the
                                    // the counter concurrently from multiple threads without any synchronization.
}

// Thread-Safe Data Structure.
// Thread-Safe Data Structure that provides all the necessary synchronization for the corresponding operations that
// need to be performed on shared state can be the general solution for parallelism problem.
val atomicIntegerCounter = AtomicInteger(0)

fun threadSafeDataStructureExample() = runBlocking {
    withContext(Dispatchers.Default) {
        massiveComputation {
            atomicIntegerCounter.incrementAndGet()
        }
    }
    println("Counter = $atomicIntegerCounter")
}

// Thread Confinement Fine-Grained
// Thread Confinement is an approach to the parallelism problem where all access to the particular shared state is
// confined to a single thread. It is typically used in UI applications, where all UI components' state are confined to
// the single event-dispatch/application thread.
var counter2 = 0
fun threadConfinementExample() = runBlocking {
    // Create single thread context for counter
    val confinedThreadForCounter = newSingleThreadContext("Thread for Counter")

    // launch massiveComputation
    withContext(Dispatchers.Default) {
        massiveComputation {
            // Confine each increment to designated thread.
            withContext(confinedThreadForCounter) {
                // Switch context from multi-threaded Dispatchers.Default to designated single thread.
                counter2++
            }
        }
    }
    println("Counter = $counter2")
    confinedThreadForCounter.close()    // Creating single thread is very expensive, so it should be either defined in
                                        // top-level and reused throughout the application, or closed right after it
                                        // is no longer needed.

    // This example works very slowly compare to other solutions, because each individual increment operation is switched
    // from the multi-threaded Dispatchers.Default context to designated single thread context.
}
