package telegramBot.internal

import dev.inmo.tgbotapi.extensions.api.bot.getMe
import dev.inmo.tgbotapi.extensions.behaviour_builder.buildBehaviourWithLongPolling
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import telegramBot.api.TgBotDependencies
import telegramBot.api.TgBotDi
import telegramBot.api.TgBotInteractor

internal class TgBotInteractorImpl(deps: TgBotDependencies) : TgBotInteractor {

    private val telegramBot = TgBotDi.telegramBot
    private val scope = CoroutineScope(Dispatchers.Default)

    init {
        TgBotDi.initialize(deps)
    }

    override suspend fun startBot() {
        telegramBot
            .buildBehaviourWithLongPolling(scope) {
                val me = getMe()
                mainLogic()
                println(me)
            }
            .join()
    }

    override fun handleReport() {

    }
}
