package coroutines

// Pipelines Example (Calculate Prime Numbers using Channel and Pipelines.)

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*

fun main() = runBlocking {
    var cur = generateNumbers(2)
    repeat(10) {
        val prime = cur.receive()
        log("$prime")
        cur = filter(cur, prime)
    }
    coroutineContext.cancelChildren()   // Cancel all the children coroutines of main runBlocking.
}

fun CoroutineScope.generateNumbers(start: Int) = produce<Int>(Dispatchers.Default) {
    var number = start
    while (true) send(number++)
}

fun CoroutineScope.filter(stream: ReceiveChannel<Int>, prime: Int) = produce<Int>(Dispatchers.Default) {
    log("Filter processor started.")
    // Take incoming stream, and remove all the numbers that are divisible by the given prime number.
    for (element in stream) if (element % prime != 0) send(element)
}

/*
    cur: 2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28....
    1st Step
      Received Value: 2 (printed)
      filter --> (prime number: 2)
      3,5,7,9,11,13,15,17,19,21,23,25,27....
    2nd Step
      Received Value: 3 (printed)
      filter --> (prime number: 3)
      5,7,11,13,17,19,23,25....
    3rd Step
      Received Value: 5 (printed)
      filter --> (prime number: 5)
      7,11,13,17,19,23....
    4th Step
      Received Value: 7 (printed)
      11,13,17,19,23...
    (Same process will be continued along the pipeline)
 */