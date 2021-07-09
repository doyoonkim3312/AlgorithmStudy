package tasks

import contributors.GitHubService
import contributors.RequestData
import contributors.User
import kotlin.concurrent.thread

fun loadContributorsBackground(service: GitHubService, req: RequestData, updateResults: (List<User>) -> Unit) {
    // thread: start a new thread.
    // Move whole computation (loadContributorsBlocking()) to a different thread.
    thread {
        val users = loadContributorsBlocking(service, req)
        // The main thread is free and can be occupied with different tasks.
        updateResults(users)    // Don't forget to call the callbacks. Programmer should make sure to explicitly call the
                                // logic passed in the callback.
    }

    /*
        Brief Review of Lambda Expression:
        ex) val lambdaEx : () -> Unit = {println("Hello!")}: lambdaEx is a function that takes no argument and no return
        value.
     */
}