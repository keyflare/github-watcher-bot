package githubApp.internal.provider

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import org.kohsuke.github.GHAppInstallation
import org.kohsuke.github.GitHub
import org.kohsuke.github.GitHubBuilder

internal class GithubInstallationProvider(
    private val rootProvider: GithubRootProvider
) {

    fun getInstallationByFullInfo(installationInfo: GHAppInstallation) : Flow<Result<GitHub>> = flow {
        emit(internalGetInstallation(installationInfo))
    }

    fun getInstallationByUser(userLogin: String) : Flow<Result<GitHub>> = flow {
        val installationInfo = rootProvider
            .gitHub
            .first()
            .app
            .getInstallationByUser(userLogin)

        emit(internalGetInstallation(installationInfo))
    }

    private fun internalGetInstallation(installationInfo: GHAppInstallation): Result<GitHub> {
        return runCatching {
            GitHubBuilder()
                .withAppInstallationToken(
                    installationInfo
                        .createToken()
                        .create()
                        .token
                )
                .build()
        }.fold(
            onSuccess = { githubInstance ->
                if (githubInstance != null) {
                    Result.success(githubInstance)
                } else {
                    Result.failure(Exception("Github instance is null"))
                }
            },
            onFailure = { e -> Result.failure(e) }
        )
    }

}
