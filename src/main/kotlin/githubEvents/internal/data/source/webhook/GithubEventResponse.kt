package githubEvents.internal.data.source.webhook

internal data class GithubEventResponse(
    val type: String,
    val payload: String,
) {
    companion object {
        const val TYPE_UNKNOWN = "unknown_type"
    }
}
