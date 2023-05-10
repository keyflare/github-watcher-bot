package githubApp.internal.report

import githubApp.api.GithubReportData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

internal class GithubReporter {

    private val _reports = MutableSharedFlow<GithubReportData>()
    val reports: Flow<GithubReportData> = _reports.asSharedFlow()

}
