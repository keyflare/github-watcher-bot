package telegramBot.internal.commands.report

import dev.inmo.tgbotapi.extensions.behaviour_builder.BehaviourContext
import dev.inmo.tgbotapi.extensions.behaviour_builder.triggers_handling.onCommand

internal suspend fun BehaviourContext.reportCommand() {
    onCommand("report") {

    }
}
