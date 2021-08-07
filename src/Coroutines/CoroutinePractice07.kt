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
    // fanInExample2()
    // bufferedChannelExample()
    // channelFIFOInvocationExample()
    tickerChannelExample()
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

// Buffered Channel
// Unlike Unbuffered Channel (aka Rendezvous), Buffered channel allows sender to send multiple elements before suspending.
fun bufferedChannelExample() = runBlocking {
    val bufferedChannel = Channel<Int>(4)   // Create Buffered Channel with 4 of its capacity.
    // Create sender Coroutine
    val sender = launch {
        repeat(10) {
            println("Sending Element $it")
            bufferedChannel.send(it)    // send() will be suspended right after sending fourth element to the Channel.
        }
    }
    // Wait 1s before canceling the sender.
    delay(1000L)
    sender.cancel()
    
    val receiver = launch {
        // Receiver Clearly shows that Buffered Channel only has first four elements sent.
        bufferedChannel.consumeEach { element ->
            println("Element $element received from the Buffered Channel.")
        }
    }
    delay(100L) // Cancel All the Child coroutines after 100ms
    receiver.cancel()

    coroutineContext.cancelChildren()
}

// Important Characteristic: Channels are FAIR.
// Send and Receive operations to Channels are FAIR with respect to the order of their invocation from multiple coroutines.
data class Ball(var hits: Int)

suspend fun player(name: String, table: Channel<Ball>) {
    for (ball in table) {   // Receive element from the Channel.
        ball.hits++
        println("$name $ball")
        delay(300L)
        table.send(ball)
    }
}

fun channelFIFOInvocationExample() = runBlocking {
    val table = Channel<Ball>() //Shared Channel.
    launch { player("Ping", table) }    // Launch First Coroutine.
    launch { player("Pong", table) }    // Launch Second Coroutine.
    table.send(Ball(0)) // Send initial element to shared channel.
    // Respect to FIFO order of invocation, "Ping" will first get initially sent element. Then, new 'Ball' element sent
    // by Ping will be received by "Pong." (Looping during 1s.)
    delay(1000L)    // Waits 1s.
    coroutineContext.cancelChildren()
}

// Ticker Channel.
// Ticker Channel is a special Rendezvous Channel that produce Kotlin.Unit evey time given delay passes since last
// consumption from this channel.
fun tickerChannelExample() = runBlocking {
    val tickerChannel = ticker(delayMillis = 100L, initialDelayMillis = 0L) // Create New Ticker Channel.
    var nextElement = withTimeoutOrNull(1L) { tickerChannel.receive() }
    println("Initial Element is available immediately: $nextElement")

    nextElement = withTimeoutOrNull(50L) { tickerChannel.receive() }
    println("Next Element is not available 50ms after its last consumption: $nextElement")

    nextElement = withTimeoutOrNull(55L) { tickerChannel.receive() }
    println("Next Element is now ready to be received: $nextElement")   // Total 105ms passed after its last consumption.

    // Emulate large consumption delays
    println("Consume will be paused for 150ms.")
    delay(150L)

    nextElement = withTimeoutOrNull(1L) { tickerChannel.receive() }
    println("Next Element is available immediately after large consumption delay: $nextElement")
    
    // IMPORTANT: Pause Between receive() calls is taken into account (next element will arrived faster.)
    // after 150ms delay --> first receive call gets element immediately. (50ms left) So, next receive call gets
    // next element in 50ms.
    nextElement = withTimeoutOrNull(55L) { tickerChannel.receive()}
    println("Next Element is ready in 50ms after consumption pauses in 150ms: $nextElement")

    tickerChannel.cancel(CancellationException("No More Elements are needed."))
}