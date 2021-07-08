package tasks

import contributors.*
import okhttp3.internal.notify
import okhttp3.internal.notifyAll
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import java.util.concurrent.atomic.AtomicInteger

fun loadContributorsCallbacks(service: GitHubService, req: RequestData, updateResults: (List<User>) -> Unit) {
    service.getOrgReposCall(req.org).onResponse { responseRepos ->
        logRepos(req, responseRepos)
        val repos = responseRepos.bodyList()
        val allUsers = mutableListOf<User>()
        var cnt = 0
        for ((index, repo) in repos.withIndex()) {
            // Callbacks that occurred concurrently.
            service.getRepoContributorsCall(req.org, repo.name).onResponse { responseUsers ->
                logUsers(repo, responseUsers)
                val users = responseUsers.bodyList()
                allUsers += users
                // if (index == repos.lastIndex) updateResults(allUsers.aggregate())   // This code also doesn't work as expected.
                cnt++
                if (cnt == repos.size) updateResults(allUsers.aggregate())
            }
        }
        // TODO: Why this code doesn't work? How to fix that?
        // Reason: Since 'CallBacks' are occurred concurrently, program does not need to wait for the
        // loaded result. If 'updateResults()' method is called at this position, that method will be
        // executed right after the loading requests are started. Because 'CallBacks' are occurred co
        // -ncurrently, allUsers list is not filled yet. That's why this code doesn't work.
        // updateResults(allUsers.aggregate())
    }
}

inline fun <T> Call<T>.onResponse(crossinline callback: (Response<T>) -> Unit) {
    enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            if (response.isSuccessful) callback(response)
            else log.error("RESPONSE WAS NOT SUCCESSFUL")
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            log.error("Call failed", t)
        }
    })
}
