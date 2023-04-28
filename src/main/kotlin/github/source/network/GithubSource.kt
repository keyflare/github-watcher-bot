package github.source.network

import io.ktor.client.*
import io.ktor.server.util.*

class GithubSource {

    val client = HttpClient {
        url { "//todo" }
    }

    suspend fun getRepositoryInfo(): GithubRepositoryInfoResponse {
        return GithubRepositoryInfoResponse(
            test = "test",
        )
    }
}
