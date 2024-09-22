package navid.multiplash.feature.topic.usecase

import navid.multiplash.core.data.Photo
import navid.multiplash.core.data.User
import navid.multiplash.feature.topic.data.remote.TopicClient

internal fun interface GetTopicUseCase {

    suspend operator fun invoke(topicId: String): Result<Topic>

    data class Topic(
        val id: String,
        val title: String,
        val description: String,
        val totalPhotos: Int,
        val owners: String,
        val topContributors: List<User>,
        val coverPhoto: Photo,
    )
}

internal class GetTopicUseCaseImpl(
    private val topicClient: TopicClient,
) : GetTopicUseCase {

    override suspend fun invoke(topicId: String): Result<GetTopicUseCase.Topic> = topicClient.getTopic(topicId).fold(
        onSuccess = { response ->
            Result.success(
                GetTopicUseCase.Topic(
                    id = response.topic.id,
                    title = response.topic.title,
                    description = response.topic.description,
                    totalPhotos = response.topic.totalPhotos,
                    owners = "by ${response.topic.owners.joinToString { it.name }}",
                    topContributors = response.topic.topContributors ?: emptyList(),
                    coverPhoto = response.topic.coverPhoto,
                )
            )
        },
        onFailure = { Result.failure(it) },
    )
}
