package githubEvents.internal.data

import githubEvents.internal.data.source.webhook.GithubEventResponse
import githubEvents.internal.data.source.webhook.GithubWebhookServer
import githubEvents.internal.domain.GithubEventsRepository
import supportedGithubEvents.api.GithubEventData
import supportedGithubEvents.api.GithubEventType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class GithubEventsRepositoryImpl(
    private val webhookServer: GithubWebhookServer,
) : GithubEventsRepository {

    override val events: Flow<GithubEventData> = webhookServer.events.mapEvents()

    override fun subscribe() {
        webhookServer.start()
    }

    private fun Flow<GithubEventResponse>.mapEvents(): Flow<GithubEventData> {
        return map { response ->
            GithubEventData(
                type = response.type.mapEventType(),
                payload = response.payload,
            )
        }
    }

    private fun String.mapEventType(): GithubEventType {
        return when (this) {
            "pull_request" -> GithubEventType.PULL_REQUEST
            "ping" -> GithubEventType.PING

            GithubEventResponse.TYPE_UNKNOWN -> {
                logError(
                    message = "Unknown event type",
                    content = this,
                )
                GithubEventType.UNKNOWN
            }
            else -> {
                logError(
                    message = "Unsupported event type",
                    content = this,
                )
                GithubEventType.UNKNOWN
            }
        }
    }

    private fun logError(message: String, content: Any? = null) {
        val logMessage = "GithubEventRepository:\n    $message".let {
            if (content != null) {
                "$it\n    content: $content"
            } else {
                it
            }
        }
        println(logMessage)
    }

}
