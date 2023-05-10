package githubApp.internal.provider

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import org.kohsuke.github.GHPullRequest

internal class GithubPullRequestProvider(
    private val repositoryProvider: GithubRepositoryProvider,
) {

    fun getPullRequest(pr: GHPullRequest): Flow<Result<GHPullRequest>> = flow {
        val repository = repositoryProvider
            .getRepositoryByFullInfo(pr.repository)
            .first()
            .fold(
                onSuccess = { repository -> repository },
                onFailure = { throwable ->
                    emit(Result.failure(throwable))
                    return@flow
                }
            )

        runCatching { repository.getPullRequest(pr.number) }
            .fold(
                onSuccess = { pullRequest ->
                    if (pullRequest == null) {
                        emit(Result.failure(NullPointerException("Pull request is null")))
                        return@fold
                    }
                    emit(Result.success(pullRequest))
                },
                onFailure = { throwable ->
                    emit(Result.failure(throwable))
                }
            )
    }
}
