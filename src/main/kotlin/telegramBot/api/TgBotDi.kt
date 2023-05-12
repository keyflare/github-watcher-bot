package telegramBot.api

import dev.inmo.tgbotapi.bot.TelegramBot
import dev.inmo.tgbotapi.extensions.api.telegramBot

internal object TgBotDi {

    ////////// External dependencies ///////////////////

    private lateinit var token: String

    fun initialize(deps: TgBotDependencies) {
        token = deps.token
    }

    ////////////////////////////////////////////////////

    val telegramBot: TelegramBot by lazy { telegramBot(token) }

}