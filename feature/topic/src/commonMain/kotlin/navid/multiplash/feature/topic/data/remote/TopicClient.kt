package navid.multiplash.feature.topic.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.http.encodedPath
import io.ktor.http.takeFrom
import navid.multiplash.core.api.config.BASE_URL
import navid.multiplash.core.api.converter.responseConverter
import navid.multiplash.core.data.Photo
import navid.multiplash.core.data.Topic
import navid.multiplash.feature.topic.data.model.GetTopic
import navid.multiplash.feature.topic.data.model.GetTopicPhotos

internal interface TopicClient {

    suspend fun getTopic(topicId: String): Result<GetTopic.Response>

    suspend fun getTopicPhotos(
        topicId: String,
        page: Int,
        pageSize: Int,
    ): Result<GetTopicPhotos.Response>
}

internal class TopicClientImpl(
    private val httpClient: HttpClient,
) : TopicClient {

    override suspend fun getTopic(topicId: String): Result<GetTopic.Response> = responseConverter<Topic>(
        httpClient.get {
            url {
                takeFrom(BASE_URL)
                encodedPath = GetTopic.getPath(topicId)
            }
        }
    ).fold(
        onSuccess = { Result.success(GetTopic.Response(topic = it)) },
        onFailure = { Result.failure(it) },
    )

    override suspend fun getTopicPhotos(
        topicId: String,
        page: Int,
        pageSize: Int,
    ): Result<GetTopicPhotos.Response> =
        responseConverter<List<Photo>>(
            httpClient.get {
                url {
                    takeFrom(BASE_URL)
                    encodedPath = GetTopicPhotos.getPath(topicId)
                    parameters.appendAll(GetTopicPhotos.getQueryParameters(page, pageSize))
                }
            }
        ).fold(
            onSuccess = { Result.success(GetTopicPhotos.Response(photos = it)) },
            onFailure = { Result.failure(it) },
        )
}
