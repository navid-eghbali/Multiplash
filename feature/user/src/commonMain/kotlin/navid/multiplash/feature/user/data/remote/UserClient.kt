package navid.multiplash.feature.user.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.http.encodedPath
import io.ktor.http.takeFrom
import navid.multiplash.core.api.config.BASE_URL
import navid.multiplash.core.api.converter.responseConverter
import navid.multiplash.core.data.Photo
import navid.multiplash.feature.user.data.model.GetUser
import navid.multiplash.feature.user.data.model.GetUserPhotos
import navid.multiplash.feature.user.data.model.GetUserStatistics

internal interface UserClient {

    suspend fun getUser(username: String): Result<GetUser.Response>

    suspend fun getUserStatistics(username: String): Result<GetUserStatistics.Response>

    suspend fun getUserPhotos(
        username: String,
        page: Int,
        pageSize: Int,
    ): Result<GetUserPhotos.Response>
}

internal class UserClientImpl(
    private val httpClient: HttpClient,
) : UserClient {
    override suspend fun getUser(username: String): Result<GetUser.Response> = responseConverter<GetUser.Response>(
        httpClient.get {
            url {
                takeFrom(BASE_URL)
                encodedPath = GetUser.getPath(username)
            }
        }
    ).fold(
        onSuccess = { Result.success(it) },
        onFailure = { Result.failure(it) },
    )

    override suspend fun getUserStatistics(username: String): Result<GetUserStatistics.Response> = responseConverter<GetUserStatistics.Response>(
        httpClient.get {
            url {
                takeFrom(BASE_URL)
                encodedPath = GetUserStatistics.getPath(username)
            }
        }
    ).fold(
        onSuccess = { Result.success(it) },
        onFailure = { Result.failure(it) },
    )

    override suspend fun getUserPhotos(
        username: String,
        page: Int,
        pageSize: Int,
    ): Result<GetUserPhotos.Response> =
        responseConverter<List<Photo>>(
            httpClient.get {
                url {
                    takeFrom(BASE_URL)
                    encodedPath = GetUserPhotos.getPath(username)
                    parameters.appendAll(GetUserPhotos.getQueryParameters(page, pageSize))
                }
            }
        ).fold(
            onSuccess = { Result.success(GetUserPhotos.Response(photos = it)) },
            onFailure = { Result.failure(it) },
        )
}
