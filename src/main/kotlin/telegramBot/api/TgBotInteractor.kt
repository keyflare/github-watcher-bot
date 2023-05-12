package telegramBot.api

import telegramBot.internal.TgBotInteractorImpl

public interface TgBotInteractor {

    public suspend fun startBot()
    public fun handleReport()

    public companion object {
        public operator fun invoke(deps: TgBotDependencies): TgBotInteractor = TgBotInteractorImpl(deps)
    }
}
