package supportedGithubReports.api

public sealed interface GithubReportData {

    public data class ReviewersReportData(
        val repositoryName: String,
        val goodReviewers: List<String>,
        val badReviewers: List<String>,
    ) : GithubReportData {

        public data class Reviewer(
            val name: String,
        )
    }
}
