package githubApp.internal

import githubApp.api.GithubAppDependencies
import githubApp.api.GithubAppInteractor
import githubApp.api.GithubReportData
import githubApp.internal.events.EventHandler
import githubApp.internal.report.GithubReporter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import supportedGithubEvents.api.GithubEventData

internal class GithubAppInteractorImpl(
    deps: GithubAppDependencies
) : GithubAppInteractor {

    init {
        GithubDi.initialize(deps)
    }

    private val eventHandler: EventHandler = GithubDi.eventHandler
    private val reporter: GithubReporter = GithubDi.reporter

    @Volatile
    private var started = false

    override val reports: Flow<GithubReportData> = reporter.reports

    override fun startInteraction(): Flow<Result<Unit>> = flow {
        check(!started)
        started = true
        emit(Result.success(Unit))
    }

    override fun stopInteraction() {
        check(started)
        started = false
    }

    override suspend fun handleEvent(event: GithubEventData) {
        check(started)
        eventHandler.handleEvent(event)
    }

}
