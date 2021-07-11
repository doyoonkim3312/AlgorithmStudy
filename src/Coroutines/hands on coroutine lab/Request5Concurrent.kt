package tasks

import contributors.*
import kotlinx.coroutines.*

suspend fun loadContributorsConcurrent(service: GitHubService, req: RequestData): List<User> = coroutineScope {
    val repos = service
        .getOrgRepos(req.org)
        .also { logRepos(req, it) }
        .body() ?: listOf()

    val responses: List<Deferred<List<User>>> = repos.map { repo ->
        // It is considered good practice to use Dispatcher from outer scope rather than to explicitly specify it
        // on each end-point. (For this code, launch method in Contributors.kt uses Dispatcher.Default. So async
        // method below will use Dispatcher from outer scope, since there's no Dispatcher explicitly specified.
        async {
            log("starting loading for ${repo.name}")
            // Test for cancellation
            delay(3000L)
            service.getRepoContributors(req.org, repo.name)
                .also { logUsers(repo, it) }
                .bodyList()
        }
    }
    responses.awaitAll().flatten().aggregate()
}