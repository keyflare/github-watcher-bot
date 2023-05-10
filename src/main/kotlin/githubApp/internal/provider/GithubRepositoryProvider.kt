package githubApp.internal.provider

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import org.kohsuke.github.GHRepository

internal class GithubRepositoryProvider(
    private val installationProvider: GithubInstallationProvider,
) {

    fun getRepositoryByFullInfo(repository: GHRepository): Flow<Result<GHRepository>> = flow {
        val installation = installationProvider
            .getInstallationByUser(repository.ownerName)
            .first()
            .fold(
                onSuccess = { installation -> installation },
                onFailure = { throwable ->
                    emit(Result.failure(throwable))
                    return@flow
                }
            )

        runCatching { installation.getRepositoryById(repository.id) }
            .fold(
                onSuccess = { repo ->
                    if (repo == null) {
                        emit(Result.failure(NullPointerException("Repository is null")))
                        return@fold
                    }
                    emit(Result.success(repo))
                },
                onFailure = { throwable ->
                    emit(Result.failure(throwable))
                }
            )
    }
}
