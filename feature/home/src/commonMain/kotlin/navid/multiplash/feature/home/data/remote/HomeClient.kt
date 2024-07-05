package navid.multiplash.feature.home.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.http.encodedPath
import io.ktor.http.takeFrom
import navid.multiplash.core.api.config.BASE_URL
import navid.multiplash.core.api.converter.responseConverter
import navid.multiplash.feature.home.data.model.GetPhotos
import navid.multiplash.feature.home.data.model.Photo

interface HomeClient {

    suspend fun getPhotos(
        page: Int,
        pageSize: Int,
    ): Result<GetPhotos.Response>
}

internal class HomeClientImpl(
    private val httpClient: HttpClient,
) : HomeClient {

    override suspend fun getPhotos(
        page: Int,
        pageSize: Int,
    ): Result<GetPhotos.Response> =
        responseConverter<List<Photo>> {
            httpClient.get {
                url {
                    takeFrom(BASE_URL)
                    encodedPath = GetPhotos.getPath()
                    parameters.appendAll(GetPhotos.getQueryParameters(page, pageSize))
                }
            }
        }.fold(
            onSuccess = { Result.success(GetPhotos.Response(photos = it)) },
            onFailure = { Result.failure(it) },
        )
}
