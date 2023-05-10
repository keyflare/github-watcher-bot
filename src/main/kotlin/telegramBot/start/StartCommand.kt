package telegramBot.start

import dev.inmo.tgbotapi.extensions.api.send.reply
import dev.inmo.tgbotapi.extensions.behaviour_builder.BehaviourContext
import dev.inmo.tgbotapi.extensions.behaviour_builder.triggers_handling.onCommand

private var state = StartState.NOT_STARTED

private enum class StartState {
    NOT_STARTED,
    INPUT_GITHUB_REPO,
    STARTED,
}

internal suspend fun BehaviourContext.startCommand() {
    onCommand("start", requireOnlyCommandInMessage = true) {
        val starting = true
//        core.Di.session.chat = it.chat
        reply(it, "Введите время, в которое хотите получать репорт, в формате \"13:30\"")
    }
//    onContentMessage {
//        if ()
//    }
}
