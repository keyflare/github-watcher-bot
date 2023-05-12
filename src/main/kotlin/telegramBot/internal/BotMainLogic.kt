package telegramBot.internal

import telegramBot.internal.debug.debugLogging
import telegramBot.internal.commands.start.startCommand
import dev.inmo.tgbotapi.extensions.behaviour_builder.BehaviourContext
import telegramBot.internal.commands.report.reportCommand

internal suspend fun BehaviourContext.mainLogic() {
    startCommand()
    reportCommand()

    debugLogging()
}


