package app

import githubEvents.api.GithubEventsInteractor
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first

/**
 * This method by default expects one argument in [args] field: telegram bot configuration
 */
public suspend fun main(args: Array<String>) {
    AppDi.initialize(args.toList())
    startGithubApp(subscribeEvents())
    startTelegramBot()
}

private fun subscribeEvents(): GithubEventsInteractor {
    return AppDi.githubEventsInteractor.apply { start() }
}

private suspend fun startGithubApp(githubEventsInteractor: GithubEventsInteractor): Boolean {
    val interactor = AppDi.githubAppInteractor

    interactor
        .startInteraction()
        .first()
        .fold(
            onSuccess = {},
            onFailure = { return false },
        )

    withContext(Dispatchers.Default) {
        githubEventsInteractor.events.collect { event ->
            interactor.handleEvent(event)
        }
    }

    return true
}

private suspend fun startTelegramBot() {
    AppDi.telegramBotInteractor.startBot()
}
