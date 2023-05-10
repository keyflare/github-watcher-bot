package githubEvents.internal.data.source.webhook

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

internal class GithubWebhookServer(private val webhookPort: Int) {

    private val _events = MutableSharedFlow<GithubEventResponse>()
    val events: Flow<GithubEventResponse> = _events.asSharedFlow()

    private var engine: NettyApplicationEngine? = null

    fun start() {
        engine = createEngine(webhookPort).also { it.start(wait = false) }
    }

    private fun createEngine(port: Int): NettyApplicationEngine {
        return embeddedServer(factory = Netty, port = port) {
            routing {
                get(path = "/ping") {
                    log(call)
                    call.respondText("pong")
                }
                post(path = "/events") {
                    log(call)
                    _events.emit(
                        GithubEventResponse(
                            type = call.request.header(EVENT_TYPE_HEADER) ?: GithubEventResponse.TYPE_UNKNOWN,
                            payload = call.receiveText(),
                        )
                    )
                    call.respond(HttpStatusCode.OK)
                }
            }
        }
    }

    private fun log(content: Any?) {
        println("GithubEventServer: $content")
    }

    companion object {
        const val EVENT_TYPE_HEADER = "X-GitHub-Event"
    }
}
