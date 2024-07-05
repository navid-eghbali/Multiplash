package navid.multiplash.core.api.converter

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse

suspend inline fun <reified T> responseConverter(
    block: () -> HttpResponse,
): Result<T> = runCatching { block().body<T>() }
