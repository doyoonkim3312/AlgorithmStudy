package coroutines

/*
    July 27, 2021 - Scopes, Lifecycle, Thread-Local Data.
 */

import kotlinx.coroutines.*

fun main() {
    // coroutineLifecycleExample()
    threadLocalDataExample()
}

class Activity {
    // Using Dispatchers.Default for test purpose.
    private val mainScope = CoroutineScope(Dispatchers.Default)

    fun destroy() {
        // Coroutine will be cancelled when this Activity is destroyed.
        mainScope.cancel()
    }

    fun doSomething() {
        // Test Coroutines. (Illustrates some computation that takes some time.)
        repeat(10) {
            mainScope.launch {
                delay((it + 1) * 200L)
                println("Coroutine $it is done.")
            }
        }
    }
}

fun coroutineLifecycleExample() = runBlocking {
    val testActivity = Activity()
    testActivity.doSomething()
    println("Launch Coroutines")
    delay(500L)
    println("Destroying activity!")
    testActivity.destroy()
    delay(1000)
}

// Coroutine and Thread-Local data.
val threadLocal = ThreadLocal<String?>()

fun threadLocalDataExample() = runBlocking {
    threadLocal.set("Main")
    // println(threadLocal.ensurePresent())
    println("Pre-Main: Current Thread: ${Thread.currentThread()}, Current Thread-Local Value: ${threadLocal.get()}")
    val job = launch(Dispatchers.Default + threadLocal.asContextElement(value = "Launch")) {
        println("Launch Start, Current Thread: ${Thread.currentThread()}, Current Thread-Local Value: ${threadLocal.get()}")
        yield()
        println("After Launch, Current Thread: ${Thread.currentThread()}, Current Thread-Local Value: ${threadLocal.get()}")
    }
    job.join()
    println("Post-Main: Current Thread: ${Thread.currentThread()}, Current Thread-Local Value: ${threadLocal.get()}")
}

// The Thread-Local variable accessed from the coroutine might have an unexpected value, if the thread running the
// coroutine is different. To avoid this risk, it is recommended to use ensurePresent() method and fail-fast on im-
// proper usages.
public suspend inline fun <T> ThreadLocal<T>.getSafety(): T {
    ensurePresent()
    return get()
}

