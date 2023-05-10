package app

import dev.inmo.tgbotapi.bot.TelegramBot
import dev.inmo.tgbotapi.extensions.api.telegramBot
import githubApp.api.GithubAppDependencies
import githubApp.api.GithubAppInteractor
import githubEvents.api.GithubEventsDependencies
import githubEvents.api.GithubEventsInteractor
import kotlinx.serialization.json.Json

internal object AppDi {

    ////////// Dependencies from app arguments ///////////////////

    private lateinit var configPath: String

    fun initialize(args: List<String>) {
        configPath = args.first()
    }

    //////////////////////////////////////////////////////////////

    val json = Json { ignoreUnknownKeys = false }
    private val startupConfig: StartupConfig by lazy { StartupConfig.load(configPath) }

    // Telegram Bot
    val telegramBot: TelegramBot by lazy { telegramBot(startupConfig.telegramToken) }

    // Github App
    private val githubAppDeps: GithubAppDependencies = object : GithubAppDependencies {
        override val applicationId get() = startupConfig.githubApplicationId
        override val clientSecret get() = startupConfig.githubClientSecret
        override val privateKeyPath get() = startupConfig.githubPrivateKeyPath
    }
    val githubAppInteractor: GithubAppInteractor by lazy { GithubAppInteractor(githubAppDeps) }

    // Github Events
    private val githubEventsDeps: GithubEventsDependencies = object : GithubEventsDependencies {
        override val webhookUrl get() = startupConfig.githubWebhookUrl
        override val webhookServerPort get() = startupConfig.githubWebhookServerPort
    }
    val githubEventsInteractor: GithubEventsInteractor by lazy { GithubEventsInteractor(githubEventsDeps) }

}
