package navid.multiplash.core.api.converter

import io.ktor.client.call.body
import io.ktor.client.plugins.ResponseException
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.JsonConvertException
import kotlinx.serialization.json.Json
import navid.multiplash.core.api.data.ApiException
import navid.multiplash.core.api.data.Failure

suspend inline fun <reified T> responseConverter(
    response: HttpResponse,
): Result<T> = runCatching { response.body<T>() }
    .fold(
        onSuccess = { Result.success(it) },
        onFailure = { throwable ->
            when (throwable) {
                is JsonConvertException -> {
                    val failure = response.body<Failure>()
                    Result.failure(ApiException(failure.toString()))
                }

                is ResponseException -> {
                    val failureText = throwable.response.bodyAsText()
                    val failure = Json.decodeFromString(Failure.serializer(), failureText)
                    Result.failure(ApiException(failure.toString()))
                }

                else -> Result.failure(throwable)
            }
        },
    )
