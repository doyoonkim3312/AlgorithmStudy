package coroutines

/*
    Asynchronous Flow
 */

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.coroutines.CoroutineContext
import kotlin.system.measureTimeMillis

fun main() {
    // simpleSequenceType().forEach { number -> println(number) }
    // runBlocking<Unit> { simpleSuspendingFunctionType().forEach{ number -> println(number)}}
    // flowTypeExample()
    // coldStreamExample()
    // flowCancellationExample()
    // flowBuilderExample()
    // intermediateOperators()
    // sizeLimitingOperatorExample()
    // terminalOperatorsExample()
    // sequentialOrderOfFlowProcessingExample()
    // flowContextExample()
    // println("==============================")
    // flowOnExample()
    // bufferOperatorExample()
    // conflateOperatorExample()
    // collectLatestOperatorExample()
    // zipOperatorExample()
    // combineOperatorExample()
    // flatMapConcatOperatorExample()
    flatMapMergeOperatorExample()
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
    // .map operator
    (1..3).asFlow()
        .map { value -> processRequest(value) } // Code inside of intermediate operator can call suspending function.
        .collect { response -> println(response) }

    // .filter operator
    (1..10).asFlow()
        .filter { value -> value % 2 == 0 }
        .collect { result -> println(result) }

    // .transform operator
    (1..3).asFlow()
        .transform { request ->
            emit("Making Request $request")
            emit(processRequest(request))
        }.collect { response -> println(response) }
}

// Size-limiting operators (.take())
// Cancellation performed by .take() function involves Coroutine Cancellation, so all resource-management function (like
// try-catch) works normally in this case.
fun numbersInFlow(): Flow<Int> {
    return flow<Int> {
        // CancellationException will be thrown by .take() operator.
            try {
                emit(1)
                emit(2) // .take() function limits execution of flow after second int value is emitted.
                emit(3)
            } finally {
                println("Task Completed.")
            }
        }
}

fun sizeLimitingOperatorExample() = runBlocking {
    numbersInFlow().take(2).collect { result -> println(result) }
}

// Terminal Operators
fun terminalOperatorsExample() = runBlocking {
    val testFlow: Flow<Int> = (1..5).asFlow().map { it * it }

    val flowToList: List<Int> = testFlow.toList()

    val flowToSet: Set<Int> = testFlow.toSet()

    // .first() operator that emits only 'first' value in Flow. NoSuchElementException can be thrown if the Flow is empty.
    val firstOperatorExample: Int = testFlow.first()

    // .reduce operator
    val reduceOperatorExample: Int = testFlow.reduce { accumulator, value -> accumulator + value }  // Sum

    // .fold operator
    // initial: initial value for operation.
    // operation: declare operation performed.
    val foldOperatorExample: Int = testFlow.fold(1 ,operation = {acc, value -> acc * value})

    println(flowToList) // [1, 4, 9, 16, 25]

    println(flowToSet)  // [1, 4, 9, 16, 25]

    println(firstOperatorExample)   // 1

    println(reduceOperatorExample)  // 55

    println(foldOperatorExample)    // 14400 (initial: 1)
                                    // 144000 (initial: 10)
}

// Flows are Sequential.
// Each collection works directly in the coroutine that calls terminal operator.
// EACH emitted value is processed by all the intermediate operators and passed to terminal operator.
fun sequentialOrderOfFlowProcessingExample() = runBlocking {
    (1..5).asFlow()
        .filter {
            println("Filter $it")
            it % 2 == 0
        }
        .map {
            println("Map $it")
            "String $it"
        }.collect { response -> println("Responses: $response") }
}

// Flow Context
// By default, code in the flow builder runs in the context that is provided by a collector of the corresponding flow.
fun simpleReturnsFlowWithLog(): Flow<Int> {
    return flow {
        log("Started Simple Flow with Log")
        for (i in 1..5) {
            emit(i)
        }
    }
}

fun flowContextExample() = runBlocking {
    log("Main Coroutine Started.")
    withContext(newSingleThreadContext("Thread for Flow")) {
        simpleReturnsFlowWithLog().collect { response -> log("Collected $response") }
    }
}

// flowOn() Operator: flowOn() function can be used to change the context of the flow emission in proper way.
fun simpleEmitsValueInDifferentContext(): Flow<Int> {
    return flow {
        for (i in 1..5) {
            delay(100L) // Computing in CPU-consuming way.
            log("Emitting $i")
            emit(i)
        }
    }.flowOn(Dispatchers.Default)   // Right way to change context for CPU-consuming code in flow builder
    // flowOn() operator creates another coroutine for an upstream flow when it has to change the Coroutine Dispatcher
    // in its context.
}

fun flowOnExample() = runBlocking {
    log("Main Coroutine Started")
    simpleEmitsValueInDifferentContext().collect { value -> log("Collected $value") }
}

// Buffer Operator
// Note: flowOn operator uses the same buffering mechanism when it has to change a CoroutineDispatcher, but buffer
// operator can do the same thing without changing the execution context.
fun simpleWithBufferOperator(): Flow<Int> = flow {
    for (i in 1..3) {
        delay(100L)
        emit(i)
    }
}

fun bufferOperatorExample() = runBlocking {
    val time = measureTimeMillis {
        simpleWithBufferOperator()
            .buffer()
            .collect { response ->
                delay(300L)
                log("Collected $response")
            }
    }
    log("TASK COMPLETED in $time ms.")
}

// Conflate Operator
// Instead of processing each values, conflate operator process ONLY most recent ones.
fun conflateOperatorExample() = runBlocking {
    val time = measureTimeMillis {
        simpleWithBufferOperator()
            .conflate()
            .collect { response ->
                delay(300L)
                log("Collected $response")
            }
    }
    log("TASK COMPLETED in $time ms.")
}

// xxxLatest operator family
// Another way of speeding up the processing of flow. (cancel and restart a slow collector everytime a new value is emitted.
// Ex. collectLatest -> Collecting only the most recent value.
fun collectLatestOperatorExample() = runBlocking {
    val time = measureTimeMillis {
        simpleWithBufferOperator()
            .collectLatest { response ->    // cancel and restart collector until it reaches its latest value.
                log("Collect $response")    // New values are emitted every 100ms.
                delay(300L) // Only the latest value will take 300ms to process.
                log("Processed $response")
            }
    }
    log("TASK COMPLETED in $time ms")
}

// Composing Two Flow.
// .zip() operator: compose corresponding values of two flows
fun zipOperatorExample() = runBlocking {
    val firstFlow: Flow<Int> = (1..3).asFlow()
    val secondFlow: Flow<String> = flowOf("a", "b", "c")

    // Compose corresponding values of two flows.
    firstFlow.zip(secondFlow) { value1, value2 ->
        "Composed Result: $value1 from first Flow, $value2 from second Flow"
    }.collect { response ->
        println("Collected: $response")
    }
}

// .combine() operator
// If flow represents the most recent value, composed value must be recomputed evey time a new value is emitted from flow.
fun combineOperatorExample() = runBlocking {
    // onEach{...} intermediate operator: Code inside of onEach{...} operator executed every time each value is
    // processed.
    val firstFlow: Flow<Int> = (1..3).asFlow().onEach { delay(300L) }
    val secondFlow: Flow<String> = flowOf("a", "b", "c").onEach { delay(400L) }

    val startTime = System.currentTimeMillis()

    // Compose value by combine operator.
    firstFlow.combine(secondFlow) { value1, value2 ->
        "Combined $value1 and $value2"
    }.collect { response ->
        println("Collected $response at ${System.currentTimeMillis() - startTime} ms.")
    }
}

// flattening flows
fun requestFlow(i: Int): Flow<String> = flow {
    emit("$i, First")
    delay(500L)
    emit("$i, Second")
}

// flatMapConcat
// Concatenation Mode
fun flatMapConcatOperatorExample() = runBlocking {
    val startTime = System.currentTimeMillis()
    (1..3).asFlow().onEach { delay(100L) }
        .flatMapConcat { value ->
            requestFlow(value)
        }.collect { response ->
            println("Collected $response at ${System.currentTimeMillis() - startTime} ms.")
        }
}

// flatMapMerge
// Concurrently process multiple flows and merge the result into a single flow. (Values are emitted as soon as possible)
fun flatMapMergeOperatorExample() = runBlocking {
    val startTime = System.currentTimeMillis()
    (1..3).asFlow()
        .flatMapMerge { value ->
            requestFlow(value)
        }.collect { response ->
            println("Collected $response at ${System.currentTimeMillis() - startTime} ms.")
        }
}
