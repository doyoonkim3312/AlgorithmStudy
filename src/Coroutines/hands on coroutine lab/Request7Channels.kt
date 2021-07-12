package tasks

import contributors.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.channels.withIndex

suspend fun loadContributorsChannels(
    service: GitHubService,
    req: RequestData,
    updateResults: suspend (List<User>, completed: Boolean) -> Unit
) {
    // Answer 2: (Using launch)
    coroutineScope {
        val repos = service.getOrgRepos(req.org)
            .also { logRepos(req, it) }
            .bodyList()

        val mainChannel = Channel<List<User>>() //Rendezvous Channel
        val allUsers = mutableListOf<User>()

        for (repo in repos) {
            // start producer coroutines. Remember! Coroutine is inexpensive!
            launch {
                // Producer
                val request = service.getRepoContributors(req.org, repo.name)
                    .also { logUsers(repo, it) }
                    .bodyList()
                mainChannel.send(request)
            }
        }

        launch {
            repeat(repos.size) {
                allUsers += mainChannel.receive()
                updateResults(allUsers.aggregate(), it == repos.lastIndex)
            }
        }
    }
}

/*
    Answer1 (Using async):
    val channel = Channel<List<User>>(UNLIMITED)
        val allUsers = mutableListOf<User>()

        val repos = service
            .getOrgRepos(req.org)
            .also { logRepos(req, it) }
            .bodyList()

        val users: List<Deferred<List<User>>> = repos.map { repo ->
            async {
                // Sender
                val request = service.getRepoContributors(req.org, repo.name)
                    .also { logUsers(repo, it) }
                    .bodyList()
                channel.send(request)
                request
            }
        }

        launch {
            // Receiver
            for (element: List<User> in channel) {
                allUsers += element
                updateResults(allUsers.aggregate(), false)
            }
        }
        updateResults(users.awaitAll().flatten().aggregate(), true)
 */
