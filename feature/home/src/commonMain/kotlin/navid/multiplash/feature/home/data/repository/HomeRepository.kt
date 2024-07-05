package navid.multiplash.feature.home.data.repository

import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import app.cash.paging.PagingData
import kotlinx.coroutines.flow.Flow
import navid.multiplash.feature.home.data.model.Photo
import navid.multiplash.feature.home.data.paging.PhotosPagingSource
import navid.multiplash.feature.home.data.remote.HomeClient

internal interface HomeRepository {

    fun getPhotos(): Flow<PagingData<Photo>>
}

internal class HomeRepositoryImpl(
    private val homeClient: HomeClient,
) : HomeRepository {

    override fun getPhotos(): Flow<PagingData<Photo>> =
        Pager(
            config = PagingConfig(pageSize = PhotosPagingSource.PAGE_SIZE),
            pagingSourceFactory = { PhotosPagingSource(homeClient) },
        ).flow
}
