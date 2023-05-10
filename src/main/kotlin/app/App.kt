package app

import telegramBot.mainLogic
import dev.inmo.tgbotapi.extensions.api.bot.getMe
import dev.inmo.tgbotapi.extensions.behaviour_builder.buildBehaviourWithLongPolling
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
    val bot = AppDi.telegramBot
    val scope = CoroutineScope(Dispatchers.Default)

    bot.buildBehaviourWithLongPolling(scope) {
        val me = getMe()
        mainLogic()
        println(me)
    }.join()
}
