package navid.multiplash.feature.topic.usecase

import navid.multiplash.common.ext.fromHexColorToLong
import navid.multiplash.common.ext.withDecimalSeparator
import navid.multiplash.core.data.Photo
import navid.multiplash.core.data.User
import navid.multiplash.feature.topic.data.remote.TopicClient

internal fun interface GetTopicUseCase {

    suspend operator fun invoke(topicId: String): Result<Topic>

    data class Topic(
        val id: String,
        val title: String,
        val description: String,
        val totalPhotos: String,
        val owners: String,
        val topContributors: List<User>,
        val color: Long?,
        val previewPhotos: List<Photo>,
    )
}

internal class GetTopicUseCaseImpl(
    private val topicClient: TopicClient,
) : GetTopicUseCase {

    override suspend fun invoke(topicId: String): Result<GetTopicUseCase.Topic> = topicClient.getTopic(topicId).fold(
        onSuccess = { response ->
            with(response.topic) {
                Result.success(
                    GetTopicUseCase.Topic(
                        id = id,
                        title = title,
                        description = description.orEmpty(),
                        totalPhotos = "${totalPhotos.toLong().withDecimalSeparator()} photos",
                        owners = owners.joinToString { it.name },
                        topContributors = topContributors ?: emptyList(),
                        color = coverPhoto.color?.fromHexColorToLong(),
                        previewPhotos = previewPhotos,
                    )
                )
            }
        },
        onFailure = { Result.failure(it) },
    )
}
