package navid.multiplash.feature.search.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.http.encodedPath
import io.ktor.http.takeFrom
import navid.multiplash.core.api.config.BASE_URL
import navid.multiplash.core.api.converter.responseConverter
import navid.multiplash.core.data.Topic
import navid.multiplash.feature.search.data.model.GetTopics
import navid.multiplash.feature.search.data.model.SearchPhotos

internal interface SearchClient {

    suspend fun getTopics(): Result<GetTopics.Response>

    suspend fun searchPhotos(
        query: String,
        page: Int,
        pageSize: Int,
    ): Result<SearchPhotos.Response>
}

internal class SearchClientImpl(
    private val httpClient: HttpClient
) : SearchClient {

    override suspend fun getTopics(): Result<GetTopics.Response> = responseConverter<List<Topic>>(
        httpClient.get {
            url {
                takeFrom(BASE_URL)
                encodedPath = GetTopics.getPath()
                parameters.appendAll(GetTopics.getQueryParameters())
            }
        }
    ).fold(
        onSuccess = { Result.success(GetTopics.Response(topics = it)) },
        onFailure = { Result.failure(it) },
    )

    override suspend fun searchPhotos(
        query: String,
        page: Int,
        pageSize: Int
    ): Result<SearchPhotos.Response> = responseConverter(
        httpClient.get {
            url {
                takeFrom(BASE_URL)
                encodedPath = SearchPhotos.getPath()
                parameters.appendAll(SearchPhotos.getQueryParameters(query, page, pageSize))
            }
        }
    )
}
