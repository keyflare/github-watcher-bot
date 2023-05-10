package githubEvents.internal

import supportedGithubEvents.api.GithubEventData
import githubEvents.api.GithubEventsDependencies
import githubEvents.api.GithubEventsInteractor
import kotlinx.coroutines.flow.Flow

internal class GithubEventsInteractorImpl(deps: GithubEventsDependencies) : GithubEventsInteractor {

    init {
        GithubEventsDi.initialize(deps)
    }

    private val eventsRepository = GithubEventsDi.githubEventsRepository

    override val events: Flow<GithubEventData> = eventsRepository.events

    override fun start() {
        eventsRepository.subscribe()
    }

}
