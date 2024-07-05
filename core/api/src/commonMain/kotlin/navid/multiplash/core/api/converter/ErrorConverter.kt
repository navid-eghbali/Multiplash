package navid.multiplash.core.api.converter

import io.ktor.client.plugins.ResponseException
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import navid.multiplash.core.api.data.Failure

suspend inline fun errorConverter(throwable: Throwable): Failure = when (throwable) {
    is ResponseException -> {
        val failureText = throwable.response.bodyAsText()
        Json.decodeFromString(Failure.serializer(), failureText)
    }

    else -> throw throwable
}
