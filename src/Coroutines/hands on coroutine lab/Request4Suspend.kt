package tasks

import contributors.*

/*
    Task 4:
    Copy the implementation of loadContributorsBlocking into loadContributorsSuspend. Then modify it
    in a way so that the new suspend functions are used instead of ones returning Calls.
    Run the program choosing the SUSPEND option and make sure that the UI is still responsive while
    the GitHub requests are performed.
 */

suspend fun loadContributorsSuspend(service: GitHubService, req: RequestData): List<User> {
    // Since getOrgRepos() and getReoContributors() methods now return Response<T> directly,
    // execute() method no longer needs to be called.
    // If getOrgReposCall) and getRepoContributorsCall() methods are called with execute() method,
    // an error will be thrown because execute() method blocks thread while suspend function does
    // not blocks current thread. Suspend function 'suspends' coroutines, which can be treated as
    // light-weight thread.


    val repos = service
        .getOrgRepos(req.org)
        .also { logRepos(req, it) }
        .body() ?: listOf()

    return repos.flatMap { repo ->
        service
            .getRepoContributors(req.org, repo.name)
            .also { logUsers(repo, it) }
            .bodyList()
    }.aggregate()
}