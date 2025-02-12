package navid.multiplash.feature.library.data.repository

import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import app.cash.paging.PagingData
import kotlinx.coroutines.flow.Flow
import navid.multiplash.core.db.KeyValue
import navid.multiplash.core.db.KeyValueDao
import navid.multiplash.feature.library.data.paging.LibraryPagingSource

internal interface LibraryRepository {
    fun getBookmarks(): Flow<PagingData<KeyValue>>

    suspend fun getFavorites(): Result<List<KeyValue>>
}

internal class LibraryRepositoryImpl(
    private val bookmarksDao: KeyValueDao,
    private val favoritesDao: KeyValueDao,
) : LibraryRepository {
    override fun getBookmarks(): Flow<PagingData<KeyValue>> =
        Pager(
            config = PagingConfig(pageSize = LibraryPagingSource.PAGE_SIZE),
            pagingSourceFactory = { LibraryPagingSource(bookmarksDao) },
        ).flow

    override suspend fun getFavorites(): Result<List<KeyValue>> = runCatching {
        favoritesDao.getAll()
    }
}
