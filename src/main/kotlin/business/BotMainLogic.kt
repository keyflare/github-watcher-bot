package business

import Di
import dev.inmo.tgbotapi.extensions.api.send.reply
import dev.inmo.tgbotapi.extensions.behaviour_builder.BehaviourContext
import dev.inmo.tgbotapi.extensions.behaviour_builder.triggers_handling.onCommand
import kotlinx.coroutines.flow.firstOrNull

suspend fun BehaviourContext.mainLogic() {
    startCommand()
    report()
}

suspend fun BehaviourContext.startCommand() {
    onCommand("start", requireOnlyCommandInMessage = true) {
        reply(it, "Введите время, в которое хотите получать репорт, в формате \"13:30\"")
    }
}

suspend fun BehaviourContext.report() {
    val repository = Di.githubRepository

    onCommand("report") {
        val result = repository.getReviewersInfo().firstOrNull()?.test ?: "error"
        reply(it, "Репорт: $result")
    }
}
