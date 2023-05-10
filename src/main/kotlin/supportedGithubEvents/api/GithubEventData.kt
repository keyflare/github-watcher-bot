package supportedGithubEvents.api

public data class GithubEventData(
    val type: GithubEventType,
    val payload: String,
)

public enum class GithubEventType {
    PING,
    PULL_REQUEST,
    UNKNOWN,
}
