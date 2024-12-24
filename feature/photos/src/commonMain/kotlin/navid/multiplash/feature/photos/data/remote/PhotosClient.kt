package navid.multiplash.feature.photos.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.http.encodedPath
import io.ktor.http.takeFrom
import navid.multiplash.core.api.config.BASE_URL
import navid.multiplash.core.api.converter.responseConverter
import navid.multiplash.feature.photos.data.model.Photos

internal interface PhotosClient {
    suspend fun photos(
        query: String,
        page: Int,
        pageSize: Int,
    ): Result<Photos.Response>
}

internal class PhotosClientImpl(
    private val httpClient: HttpClient,
) : PhotosClient {

    override suspend fun photos(
        query: String,
        page: Int,
        pageSize: Int,
    ): Result<Photos.Response> = responseConverter(
        httpClient.get {
            url {
                takeFrom(BASE_URL)
                encodedPath = Photos.getPath()
                parameters.appendAll(Photos.getQueryParameters(query, page, pageSize))
            }
        }
    )
}
