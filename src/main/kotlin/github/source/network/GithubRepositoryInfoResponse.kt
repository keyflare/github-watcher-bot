package github.source.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GithubRepositoryInfoResponse(
    @SerialName("test")
    val test: String?,
)
