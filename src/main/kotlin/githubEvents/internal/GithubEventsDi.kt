package githubEvents.internal

import githubEvents.api.GithubEventsDependencies
import githubEvents.internal.data.GithubEventsRepositoryImpl
import githubEvents.internal.data.source.webhook.GithubWebhookServer

internal object GithubEventsDi {

    ////////// External dependencies ///////////////////

    private lateinit var webhookUrl: String
    private var webhookServerPort: Int = 0

    fun initialize(deps: GithubEventsDependencies) {
        webhookServerPort = deps.webhookServerPort
        webhookUrl = deps.webhookUrl
    }

    ////////////////////////////////////////////////////

    private val webhookServer by lazy { GithubWebhookServer(webhookServerPort) }
    val githubEventsRepository by lazy { GithubEventsRepositoryImpl(webhookServer) }

}
