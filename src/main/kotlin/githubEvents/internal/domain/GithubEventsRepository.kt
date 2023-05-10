package githubEvents.internal.domain

import supportedGithubEvents.api.GithubEventData
import kotlinx.coroutines.flow.Flow

internal interface GithubEventsRepository {
    val events: Flow<GithubEventData>
    fun subscribe()
}
