package navid.multiplash.feature.topic.data.repository

import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import app.cash.paging.PagingData
import kotlinx.coroutines.flow.Flow
import navid.multiplash.core.data.Photo
import navid.multiplash.feature.topic.data.paging.TopicPhotosPagingSource
import navid.multiplash.feature.topic.data.remote.TopicClient

internal interface TopicRepository {

    fun getTopicPhotos(topicId: String): Flow<PagingData<Photo>>
}

internal class TopicRepositoryImpl(
    private val topicClient: TopicClient,
) : TopicRepository {

    override fun getTopicPhotos(topicId: String): Flow<PagingData<Photo>> =
        Pager(
            config = PagingConfig(pageSize = TopicPhotosPagingSource.PAGE_SIZE),
            pagingSourceFactory = { TopicPhotosPagingSource(topicClient, topicId) },
        ).flow
}
