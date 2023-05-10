package githubApp.internal.provider

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.kohsuke.github.GitHub
import org.kohsuke.github.GitHubBuilder
import org.kohsuke.github.extras.authorization.JWTTokenProvider
import java.nio.file.Files
import java.nio.file.Paths
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.spec.PKCS8EncodedKeySpec

internal class GithubRootProvider(
    applicationId: String,
    privateKeyPath: String,
) {
    private val privateKey: PrivateKey

    init {
        val key = Files.readAllBytes(Paths.get(privateKeyPath))
        val keyFactory = KeyFactory.getInstance("RSA")
        val keySpec = PKCS8EncodedKeySpec(key)
        privateKey = keyFactory.generatePrivate(keySpec)
    }

    private val authProvider = JWTTokenProvider(applicationId, privateKey)

    private var _gitHub: GitHub? = null

    val gitHub: Flow<GitHub> = flow {
        _gitHub
            ?.let { emit(it) }
            ?: run { emit(authorize()) }
    }

    private fun authorize(): GitHub {
        val gitHub = GitHubBuilder()
            .withAuthorizationProvider(authProvider)
            .build()
        _gitHub = gitHub
        return gitHub
    }
}
