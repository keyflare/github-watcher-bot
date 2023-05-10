package githubApp.internal.events

import githubApp.internal.provider.GithubRootProvider
import kotlinx.coroutines.flow.first
import org.kohsuke.github.GHEventPayload
import supportedGithubEvents.api.GithubEventData
import supportedGithubEvents.api.GithubEventType
import java.io.StringReader

internal class EventHandler(
    private val rootProvider: GithubRootProvider,
    private val pullRequestEventHandler: PullRequestEventHandler,
) {

    suspend fun handleEvent(event: GithubEventData) {
        log("New event: $event")

        val payload = parseEvent(event)
            ?.also { log(content = "Parsed event: $it") }
            ?: run {
                log(content = "Invalid event payload: $event")
                return
            }

        internalHandleEvent(payload)
    }

    private suspend fun parseEvent(event: GithubEventData): GHEventPayload? {
        val typeClass = mapEventType(event.type) ?: return null

        return rootProvider
            .gitHub
            .first()
            .parseEventPayload(StringReader(event.payload), typeClass)
    }

    private fun mapEventType(type: GithubEventType): Class<out GHEventPayload>? {
        return when (type) {
            GithubEventType.PING -> GHEventPayload.Ping::class.java
            GithubEventType.PULL_REQUEST -> GHEventPayload.PullRequest::class.java
            GithubEventType.UNKNOWN -> null
        }
    }

    private suspend fun internalHandleEvent(payload: GHEventPayload) {
        when (payload) {
            is GHEventPayload.Ping -> {
                log(content = "PONG")
            }

            is GHEventPayload.PullRequest -> {
                pullRequestEventHandler.handleEvent(payload)
            }
        }
    }

    private fun log(content: Any?) {
        println("EventsHandler: $content")
    }

}
