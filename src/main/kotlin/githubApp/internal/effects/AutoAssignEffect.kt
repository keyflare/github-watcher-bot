package githubApp.internal.effects

import githubApp.internal.provider.GithubPullRequestProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.kohsuke.github.GHPullRequest

internal class AutoAssignEffect(
    private val pullRequestProvider: GithubPullRequestProvider,
) {
    private val scope = CoroutineScope(Dispatchers.IO)

    suspend fun process(pr: GHPullRequest) {
        val pullRequest = pullRequestProvider
            .getPullRequest(pr)
            .first()
            .fold(
                onSuccess = { pullRequest -> pullRequest },
                onFailure = { throwable ->
                    log(content = throwable)
                    return
                }
            )

        scope.launch {
            log("trying to assign on ${pullRequest.user.login}")
            pullRequest.addAssignees(pullRequest.user)
            log("assigned")
        }
    }

    private fun log(content: Any?) {
        println("AutoAssignEffect: $content")
    }
}
