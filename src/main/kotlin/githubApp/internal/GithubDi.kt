package githubApp.internal

import githubApp.api.GithubAppDependencies
import githubApp.internal.effects.AutoAssignEffect
import githubApp.internal.events.EventHandler
import githubApp.internal.events.PullRequestEventHandler
import githubApp.internal.provider.GithubInstallationProvider
import githubApp.internal.provider.GithubPullRequestProvider
import githubApp.internal.provider.GithubRepositoryProvider
import githubApp.internal.provider.GithubRootProvider
import githubApp.internal.report.GithubReporter

internal object GithubDi {

    ////////// External dependencies ///////////////////

    private lateinit var applicationId: String
    private lateinit var clientSecret: String
    private lateinit var privateKeyPath: String

    fun initialize(deps: GithubAppDependencies) {
        applicationId = deps.applicationId
        clientSecret = deps.clientSecret
        privateKeyPath = deps.privateKeyPath
    }

    ////////////////////////////////////////////////////

    // Providers

    private val rootProvider: GithubRootProvider by lazy {
        GithubRootProvider(
            applicationId = applicationId,
            privateKeyPath = privateKeyPath,
        )
    }
    private val installationProvider: GithubInstallationProvider by lazy {
        GithubInstallationProvider(rootProvider = rootProvider)
    }
    private val repositoryProvider: GithubRepositoryProvider by lazy {
        GithubRepositoryProvider(installationProvider = installationProvider)
    }
    private val pullRequestProvider: GithubPullRequestProvider by lazy {
        GithubPullRequestProvider(repositoryProvider = repositoryProvider)
    }

    // Events

    val eventHandler: EventHandler by lazy {
        EventHandler(
            rootProvider = rootProvider,
            pullRequestEventHandler = pullRequestEventHandler,
        )
    }
    private val pullRequestEventHandler: PullRequestEventHandler by lazy {
        PullRequestEventHandler(
            autoAssignEffect = autoAssignEffect,
        )
    }

    // Reports

    val reporter: GithubReporter by lazy {
        GithubReporter()
    }

    // Effects

    private val autoAssignEffect: AutoAssignEffect by lazy {
        AutoAssignEffect(pullRequestProvider = pullRequestProvider)
    }

}
