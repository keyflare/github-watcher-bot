package githubEvents.api

import githubEvents.internal.GithubEventsInteractorImpl
import kotlinx.coroutines.flow.Flow
import supportedGithubEvents.api.GithubEventData

public interface GithubEventsInteractor {
    public val events: Flow<GithubEventData>
    public fun start()

    public companion object {
        public operator fun invoke(deps: GithubEventsDependencies): GithubEventsInteractor {
            return GithubEventsInteractorImpl(deps)
        }
    }
}
