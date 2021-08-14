package coroutines

/*
    Shared Mutable state and Concurrency.
 */

import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.measureTimeMillis

fun main() {
    // possibleParallelismProblemExample()
    // threadSafeDataStructureExample()
    // threadConfinementExample()
    // threadConfinementCoarseGrained()
    mutexUsageExample()
}

// Parallelism Problem in Kotlin Concurrency using Multi-Threading.
// Possible parallelism problem: synchronization of access to shared mutable state.
suspend fun massiveComputation(action: suspend() -> Unit) {
    val numOfCoroutines = 100   // Total number of coroutine would be launched.
    val numOfRepeatComputation = 1000   // Total number of repeated action.

    val time = measureTimeMillis {
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
    println("${numOfCoroutines * numOfRepeatComputation} TASK COMPLETED in $time ms")
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
            log("Massive Computation started.")
            // Confine each increment to designated thread.
            withContext(confinedThreadForCounter) {
                log("Increment Started.")
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

// Thread Confinement Coarse-Grained
// In practice, thread confinement is performed in large chunks are confined to a single thread.
// Since there is no context change for each task, computation speed would be way more faster than a Fine-Grained.
var counter3 = 0

fun threadConfinementCoarseGrained() = runBlocking {
    val confinedThreadForComputation = newSingleThreadContext("Confined Thread for Large Computation.")
    // All the large chunks of computation would be confined to a single thread.
    withContext(confinedThreadForComputation) {
        massiveComputation {
            counter3++
        }
    }
    println("Counter = $counter3")
    confinedThreadForComputation.close()
}

// Mutex
// Mutex is an mutual exclusion solution, which protect all modifications of the shared state with a critical section
// that is never executed concurrently.
// SHARED MUTABLE STATE ---------LOCK///(Critical Section)///UNLOCK---------
var counter4 = 0
val mutex = Mutex()

fun mutexUsageExample() = runBlocking {
    // Launch massiveComputation with multi-threading
    withContext(Dispatchers.Default) {
        massiveComputation {
            log("Is Mutex Currently Locked? 0 ${mutex.onLock}")
            // Concept of how Mutex works.
            // These mutex usage (line 130 - 137) can be replaced by .withLock extension function.
            mutex.lock()
            try {
                //log("Is Mutex currently Locked? 1 ${mutex.isLocked}")
                counter4++
            } finally {
                mutex.unlock()
                log("Is Mutex Currently Locked? 2 ${mutex.onLock}")
            }
            //log("Is Mutex Currently Locked? 3 ${mutex.isLocked}")
        }
    }
    println("Counter = $counter4")
}