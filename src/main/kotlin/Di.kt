import github.GithubRepository
import github.source.network.GithubSource

object Di {

    private val githubSource: GithubSource = GithubSource()
    val githubRepository: GithubRepository by lazy { GithubRepository(githubSource) }

}
