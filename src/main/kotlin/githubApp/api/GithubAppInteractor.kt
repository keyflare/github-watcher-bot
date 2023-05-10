package githubApp.api

import githubApp.internal.GithubAppInteractorImpl
import kotlinx.coroutines.flow.Flow
import supportedGithubEvents.api.GithubEventData

public interface GithubAppInteractor {

    public val reports: Flow<GithubReportData>

    public fun startInteraction(): Flow<Result<Unit>>
    public fun stopInteraction()
    public suspend fun handleEvent(event: GithubEventData)

    public companion object {
        public operator fun invoke(deps: GithubAppDependencies): GithubAppInteractor {
            return GithubAppInteractorImpl(deps)
        }
    }
}
