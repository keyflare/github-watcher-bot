package telegramBot.internal.debug

import dev.inmo.tgbotapi.types.ChatIdentifier
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

@OptIn(DelicateCoroutinesApi::class)
internal class DebugBotLogger {
    private val _messages = MutableSharedFlow<BotLogMessage>()
    val messages: Flow<BotLogMessage> = _messages.asSharedFlow()

    private var enabledChatsIds = listOf(
        ""
    )

    fun message(chatId: ChatIdentifier, message: String) {
//        if (chatId !in enabledChatsIds) return
//
//        GlobalScope.launch {
//            _messages.emit(BotLogMessage(chatId, message))
//        }
    }

    data class BotLogMessage(
        val chatId: ChatIdentifier,
        val message: String
    )
}
