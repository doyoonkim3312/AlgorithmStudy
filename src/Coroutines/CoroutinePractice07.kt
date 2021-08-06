package coroutines

/*
    Channel
 */

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.launch


fun main() {
    // channelBasic()
    // closeOperatorExample()
    // channelProducerExample()
    // pipelineExample()
    // fanOutExample()
    // fanInExample()
    fanInExample2()
}

// Channel Basic
// Channel is a way of transfer a stream of values. Channel works very similar to BlockingQueue.
fun channelBasic() = runBlocking {
    val testChannel = Channel<Int>()
    launch {
        // This might be a CPU-Consuming computation or async logic.
        for (i in 1..5) testChannel.send(i * i)
    }

    repeat(5) {
        println("Received ${testChannel.receive()} from testChannel")
    }
    println("Completed")
}

// close() operator: Channel can be closed to indicate that no more elements are coming.
fun closeOperatorExample() = runBlocking {
    val testChannel = Channel<Int>()
    launch {
        for (i in 1..5) testChannel.send(i)
        testChannel.close() // close() operator sends a 'special close token' to the channel.
    }

    // Receiver can iterate channel using for loop until it reaches special 'close' operator.
    for (element in testChannel) println("Element $element received from channel.")
    println("TASK COMPLETED.")
}

// Channel Producers and Producer-Consumer pattern.
fun CoroutineScope.produceDoubled(cnt: Int): ReceiveChannel<Int> = produce {
    for (i in 1..cnt) send(i * 2)
    close() // Close Channel
}

fun channelProducerExample() = runBlocking {
    val receivedChannel = produceDoubled(10)

    // for-loop can be replaced by consumeEach{...} extension function
    receivedChannel.consumeEach { element -> println("Element $element received from channel.") }
    println("TASK COMPLETED.")
}

// Pipelines
// A pipeline is a pattern where one coroutine is producing possibly infinite stream, another coroutine/coroutines are
// consuming that stream, doing some processing and producing some other results.
fun CoroutineScope.produceNumber(): ReceiveChannel<Int> = produce<Int> {
    var start = 1
    while(true) { send(start++) }   // Send infinite stream of numbers starting from 1.
}

// Another coroutine that consumes stream from 'produceNumber(),' process it and create other stream (result)
fun CoroutineScope.square(stream: ReceiveChannel<Int>) = produce<Int> {
    for (element in stream) send(element * element)
}

fun pipelineExample() = runBlocking {
    val numbers = produceNumber()
    val squaredNumbers = square(numbers)

    // Receive first 5 elements from the channel.
    repeat(5) {
        println("Element ${squaredNumbers.receive()} received from the channel (Count: $it)")
    }
    println("Completed. Channel will be closed")
    coroutineContext.cancelChildren()   // Cancel all the children coroutines. (stop creating infinite stream of value.)
                                        // Parent: main 'runBlocking'
                                        // Children: produceNumber, square
}

// Fan-out
// Multiple coroutines may receive from the same channel, distributing work between themselves.
fun CoroutineScope.produceNumbers(): ReceiveChannel<Int> = produce {
    var x = 1
    while (true) {
        send(x++)
        delay(100L) // waits 0.1s before sending next integer.
    }
}

// Processors
// Compare to consumeEach, for-loop is more suitable and safer to perform fan-out.
fun CoroutineScope.launchProcessor(id: Int, stream: ReceiveChannel<Int>) = launch (Dispatchers.Default) {
    for (element in stream) {
        log("Processor No.$id received $element from the channel")
    }
}

// Example of Fan-Out: Multiple coroutines may receive from the same channel, distributing work between themselves.
fun fanOutExample() = runBlocking {
    val producer = produceNumbers()
    repeat(5) {
        launchProcessor(it, producer)   // Multiple processor coroutines will be launched to receive element from
                                        // the same channel.
    }
    delay(950L)
    coroutineContext.cancelChildren()
}

// Fan-In: Similar to Fan-Out, Multiple coroutines may send to the same channel, distributing work between themselves.
suspend fun sendString(channel: SendChannel<String>, string: String, time: Long) {
    // This suspend function will repeatedly send same string in a specific duration.
    while (true) {
        delay(time)
        channel.send(string)
    }
}

fun fanInExample() = runBlocking {
    val testChannel = Channel<String>()
    launch { sendString(testChannel, "BOO", 200L) }
    launch { sendString(testChannel, "FOO", 500L) }

    // receive first 6
    repeat(6) {
        log("Element ${testChannel.receive()} received from the channel.")
    }
    coroutineContext.cancelChildren()
}


fun CoroutineScope.sendString(channel: SendChannel<String>, string: String, time: Long) = launch {
    while (true) {
        delay(time)
        channel.send(string)
    }
}

fun fanInExample2() = runBlocking {
    val tempChannel = Channel<String>()
    sendString(tempChannel, "Hail", 200L)
    sendString(tempChannel, "Purdue", 500L)

    repeat(6) {
        log("Element ${tempChannel.receive()} received from the channel")
    }
    coroutineContext.cancelChildren()
}