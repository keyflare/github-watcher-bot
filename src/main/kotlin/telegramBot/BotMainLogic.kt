package telegramBot

import telegramBot.debug.debugLogging
import telegramBot.start.startCommand
import dev.inmo.tgbotapi.extensions.behaviour_builder.BehaviourContext

internal suspend fun BehaviourContext.mainLogic() {
    startCommand()
    debugLogging()
    report()
}

private suspend fun BehaviourContext.report() {
//    val repository = core.Di.githubRepository

//    onCommand("report") {
//        val result = repository.getReviewersInfo().firstOrNull()?.test ?: "error"
//        reply(it, "Репорт: $result")
//    }
}
