package navid.multiplash.feature.explore.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.http.encodedPath
import io.ktor.http.takeFrom
import navid.multiplash.core.api.config.BASE_URL
import navid.multiplash.core.api.converter.responseConverter
import navid.multiplash.core.data.Photo
import navid.multiplash.feature.explore.data.model.GetPhotos

internal interface ExploreClient {

    suspend fun getPhotos(
        page: Int,
        pageSize: Int,
    ): Result<GetPhotos.Response>
}

internal class ExploreClientImpl(
    private val httpClient: HttpClient,
) : ExploreClient {

    override suspend fun getPhotos(
        page: Int,
        pageSize: Int,
    ): Result<GetPhotos.Response> = responseConverter<List<Photo>>(
        httpClient.get {
            url {
                takeFrom(BASE_URL)
                encodedPath = GetPhotos.getPath()
                parameters.appendAll(GetPhotos.getQueryParameters(page, pageSize))
            }
        }
    ).fold(
        onSuccess = { Result.success(GetPhotos.Response(photos = it)) },
        onFailure = { Result.failure(it) },
    )
}
