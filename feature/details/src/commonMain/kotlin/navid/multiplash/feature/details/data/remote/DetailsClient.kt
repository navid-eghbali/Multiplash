package navid.multiplash.feature.details.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsBytes
import io.ktor.http.encodedPath
import io.ktor.http.takeFrom
import navid.multiplash.core.api.config.BASE_URL
import navid.multiplash.core.api.converter.responseConverter
import navid.multiplash.feature.details.data.model.GetPhoto

internal interface DetailsClient {

    suspend fun getPhoto(id: String): Result<GetPhoto.Response>

    suspend fun downloadPhoto(url: String): Result<ByteArray>
}

internal class DetailsClientImpl(
    private val httpClient: HttpClient
) : DetailsClient {

    override suspend fun getPhoto(id: String): Result<GetPhoto.Response> = responseConverter<GetPhoto.Response>(
        httpClient.get {
            url {
                takeFrom(BASE_URL)
                encodedPath = GetPhoto.getPath(id)
            }
        }
    ).fold(
        onSuccess = { Result.success(it) },
        onFailure = { Result.failure(it) },
    )

    override suspend fun downloadPhoto(url: String): Result<ByteArray> = runCatching {
        httpClient.get {
            url { takeFrom(url) }
        }.bodyAsBytes()
    }
}
