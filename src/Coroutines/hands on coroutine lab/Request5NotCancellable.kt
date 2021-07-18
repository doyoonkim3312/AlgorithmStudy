package tasks

import contributors.*
import kotlinx.coroutines.*
import kotlin.coroutines.coroutineContext

suspend fun loadContributorsNotCancellable(service: GitHubService, req: RequestData): List<User> {
    val repos = service
        .getOrgRepos(req.org)
        .also { logRepos(req, it) }
        .bodyList()
    val response: List<Deferred<List<User>>> = repos.map { repo ->
        GlobalScope.async {
            log("Loading Request started...")
            // Test cancellation
            delay(3000L)
            service.getRepoContributors(req.org, repo.name)
                .also { logUsers(repo, it) }
                .bodyList()
        }
    }
    return response.awaitAll().flatten().aggregate()
}