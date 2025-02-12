package navid.multiplash.feature.library.data.paging

import androidx.paging.PagingState
import app.cash.paging.PagingSource
import app.cash.paging.PagingSourceLoadResultError
import app.cash.paging.PagingSourceLoadResultPage
import navid.multiplash.core.db.KeyValue
import navid.multiplash.core.db.KeyValueDao

internal class LibraryPagingSource(
    private val bookmarksDao: KeyValueDao,
) : PagingSource<Int, KeyValue>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, KeyValue> = try {
        val page = params.key ?: START_PAGE
        runCatching {
            bookmarksDao.getAllPaged(
                page = page,
                pageSize = PAGE_SIZE,
            )
        }.fold(
            onSuccess = { result ->
                PagingSourceLoadResultPage(
                    data = result,
                    prevKey = if (page == START_PAGE) null else page - PAGE_SIZE,
                    nextKey = if (result.size < PAGE_SIZE) null else page + PAGE_SIZE,
                )
            },
            onFailure = { PagingSourceLoadResultError(it) },
        )
    } catch (e: Exception) {
        PagingSourceLoadResultError(e)
    }

    override fun getRefreshKey(state: PagingState<Int, KeyValue>): Int = START_PAGE

    companion object {
        private const val START_PAGE = 0
        const val PAGE_SIZE = 50
    }
}
