package navid.multiplash.feature.topic.data.paging

import androidx.paging.PagingState
import app.cash.paging.PagingSource
import app.cash.paging.PagingSourceLoadResultError
import app.cash.paging.PagingSourceLoadResultPage
import navid.multiplash.core.data.Photo
import navid.multiplash.feature.topic.data.remote.TopicClient

internal class TopicPhotosPagingSource(
    private val topicClient: TopicClient,
    private val topicId: String,
) : PagingSource<Int, Photo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> = try {
        val page = params.key ?: START_PAGE
        topicClient.getTopicPhotos(
            topicId = topicId,
            page = page,
            pageSize = PAGE_SIZE
        ).fold(
            onSuccess = { response ->
                PagingSourceLoadResultPage(
                    data = response.photos,
                    prevKey = if (page == START_PAGE) null else page - 1,
                    nextKey = if (response.photos.size < PAGE_SIZE) null else page + 1,
                )
            },
            onFailure = { PagingSourceLoadResultError(it) },
        )
    } catch (e: Exception) {
        PagingSourceLoadResultError(e)
    }

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int = START_PAGE

    companion object {
        private const val START_PAGE = 1
        const val PAGE_SIZE = 10
    }
}
