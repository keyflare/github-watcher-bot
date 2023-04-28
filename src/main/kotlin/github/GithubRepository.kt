package github

import github.source.network.GithubRepositoryInfoResponse
import github.source.network.GithubSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GithubRepository(private val source: GithubSource) {

    fun getReviewersInfo(): Flow<GithubRepositoryInfo> = flow {
        val response = source.getRepositoryInfo()
        val model = response.toModel()
        emit(model)
    }

    private fun GithubRepositoryInfoResponse.toModel(): GithubRepositoryInfo {
        return GithubRepositoryInfo(
            test = test,
        )
    }
}
