package navid.multiplash.feature.details.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.http.encodedPath
import io.ktor.http.takeFrom
import navid.multiplash.core.api.config.BASE_URL
import navid.multiplash.core.api.converter.responseConverter
import navid.multiplash.feature.details.data.model.GetPhoto

internal interface DetailsClient {

    suspend fun getPhoto(id: String): Result<GetPhoto.Response>
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
}
