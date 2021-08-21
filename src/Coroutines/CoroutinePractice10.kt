package coroutines

/*
    Select (Experimental)
 */

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.selects.*
import kotlin.system.measureTimeMillis

fun main() {
    // selectExpressionExample()
    // channelOnClosedExample()
    onSendClauseExample()
}

// Select Expression
// Select Expression makes it possible to await multiple suspending functions simultaneously and process them as
// First come, first served basis.
fun CoroutineScope.fizz() = produce<String> {
    while(true) {
        // Continuously send "Fuzz" to channel with 300 ms time delay.
        delay(300L)
        send("Fizz")
    }
}

fun CoroutineScope.buzz() = produce<String> {
    while(true) {
        // Continuously send "Fizz" to channel with 500 ms delay.
        delay(500L)
        send("Buzz")
    }
}

suspend fun selectFizzBuzz(fizz: ReceiveChannel<String>, buzz: ReceiveChannel<String>) {
    select<Unit> {      // This select block does not produce any results.
        // First onReceive Clause.
        fizz.onReceive { value ->
            println("Fizz -> $value")
        }
        // Second onReceive Clause.
        buzz.onReceive { value ->
            println("Buzz -> $value")
        }
    }
}

fun selectExpressionExample() = runBlocking {
    // Launch two children coroutines, that periodically send string values.
    val fizz = fizz()
    val buzz = buzz()

    repeat(7) {
        selectFizzBuzz(fizz, buzz)
    }
    coroutineContext.cancelChildren()
}

// Channel on Close
// onReceive fails when the channel is closed, and onReceiveCatching clause can be used to perform a specific action
// when the channel is closed.
// onReceiveCatching method is a newly added, starting from Coroutines 1.5.0. (Currently, it is impossible to use
// coroutines 1.5.0 library.)
suspend fun selectFizzOrBuzz(channelA: ReceiveChannel<String>, channelB: ReceiveChannel<String>): String =
    select<String> {
        channelA.onReceive { value: String? ->
            if (value != null && !channelA.isClosedForReceive) {
                "Channel A -> $value"
            } else {
                "Channel A is closed."
            }
        }
        channelB.onReceive { value: String? ->
            if (value != null && !channelB.isClosedForReceive) {
                "Channel B -> $value"
            } else {
                "Channel B is closed."
            }
        }

    }

fun channelOnClosedExample() = runBlocking {
    val channelA = produce<String> {
        repeat(4) { send("Fizz $it") }
    }
    val channelB = produce<String> {
        repeat(4) { send("Buzz $it") }
    }

    repeat(8) {
        println(selectFizzBuzz(channelA, channelB))
    }

    coroutineContext.cancelChildren()
}

// Selecting to Send
// Select Expression has a onSend clause that can be used for good in combination with a biased nature of selection.
fun CoroutineScope.produceInteger(side: SendChannel<Int>) = produce<Int> {
    for (num in 1..10) {
        delay(100L)
        select<Unit> {
            onSend(num) {}    // Send produced integer to a primary channel.
            side.onSend(num) {} // Send value to a side channel when a consumer of primary channel cannot keep up with
                                // it.
        }
    }
}

fun onSendClauseExample() = runBlocking {
    val sideChannel = Channel<Int>()    // Instantiate Side Channel.
    launch {    // This is extremely fast consumer of side channel.
        sideChannel.consumeEach { value -> println("Side Channel has $value") }
    }

    produceInteger(sideChannel).consumeEach {   // Consumer of primary channel.
        println("Consuming $it from Primary Channel")
        delay(250L) // 250ms delay makes this consumer impossible to keep up with elements in primary channel.
    }
    println("TASK COMPLETED.")
    coroutineContext.cancelChildren()
}