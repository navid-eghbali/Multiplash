package navid.multiplash.feature.topic.usecase

import app.cash.paging.PagingData
import kotlinx.coroutines.flow.Flow
import navid.multiplash.core.data.Photo
import navid.multiplash.feature.topic.data.repository.TopicRepository

internal fun interface GetTopicPhotosUseCase {

    operator fun invoke(topicId: String): Flow<PagingData<Photo>>
}

internal class GetTopicPhotosUseCaseImpl(
    private val topicRepository: TopicRepository,
) : GetTopicPhotosUseCase {

    override fun invoke(topicId: String): Flow<PagingData<Photo>> = topicRepository.getTopicPhotos(topicId)
}
