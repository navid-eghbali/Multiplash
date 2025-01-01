package navid.multiplash.feature.search.data.repository

import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import app.cash.paging.PagingData
import kotlinx.coroutines.flow.Flow
import navid.multiplash.core.data.Photo
import navid.multiplash.feature.search.data.paging.SearchPagingSource
import navid.multiplash.feature.search.data.remote.SearchClient

internal interface SearchRepository {

    fun searchPhotos(query: String): Flow<PagingData<Photo>>
}

internal class SearchRepositoryImpl(
    private val searchClient: SearchClient
) : SearchRepository {

    override fun searchPhotos(query: String): Flow<PagingData<Photo>> =
        Pager(
            config = PagingConfig(pageSize = SearchPagingSource.PAGE_SIZE),
            pagingSourceFactory = { SearchPagingSource(searchClient, query) },
        ).flow
}
