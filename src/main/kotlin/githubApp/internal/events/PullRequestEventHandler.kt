package githubApp.internal.events

import githubApp.internal.effects.AutoAssignEffect
import org.kohsuke.github.GHEventPayload

internal class PullRequestEventHandler(
    private val autoAssignEffect: AutoAssignEffect
) {

    suspend fun handleEvent(
        payload: GHEventPayload.PullRequest
    ) {
        val action = PullRequestAction
            .values()
            .firstOrNull { it.value == payload.action }
            ?: PullRequestAction.UNSUPPORTED

        when (action) {
            PullRequestAction.OPENED,
            PullRequestAction.REOPENED -> autoAssignEffect.process(pr = payload.pullRequest)

            PullRequestAction.UNSUPPORTED -> {
                log(content = "Unsupported pull request action: ${payload.action}")
                return
            }
        }
    }

    private fun log(content: Any?) {
        println("PullRequestEventHandler: $content")
    }
}

private enum class PullRequestAction(val value: String) {
    REOPENED("reopened"),
    OPENED("opened"),
    UNSUPPORTED("unsupported"),
}
