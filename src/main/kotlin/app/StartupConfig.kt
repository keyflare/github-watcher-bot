package app

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.io.File

@Serializable
internal data class StartupConfig(
    @SerialName("telegramToken")
    val telegramToken: String,
    @SerialName("githubApplicationId")
    val githubApplicationId: String,
    @SerialName("githubClientSecret")
    val githubClientSecret: String,
    @SerialName("githubPrivateKeyPath")
    val githubPrivateKeyPath: String,
    @SerialName("githubWebhookUrl")
    val githubWebhookUrl: String,
    @SerialName("githubWebhookServerPort")
    val githubWebhookServerPort: Int,
)

internal fun StartupConfig.Companion.load(path: String): StartupConfig {
    val configJson = File(path).readText()
    return AppDi.json.decodeFromString(serializer(), configJson)
}
