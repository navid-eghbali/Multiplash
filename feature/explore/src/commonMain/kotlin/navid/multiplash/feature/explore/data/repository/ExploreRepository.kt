package navid.multiplash.feature.explore.data.repository

import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import app.cash.paging.PagingData
import kotlinx.coroutines.flow.Flow
import navid.multiplash.core.data.Photo
import navid.multiplash.feature.explore.data.paging.PhotosPagingSource
import navid.multiplash.feature.explore.data.remote.ExploreClient

internal interface ExploreRepository {

    fun getPhotos(): Flow<PagingData<Photo>>
}

internal class ExploreRepositoryImpl(
    private val exploreClient: ExploreClient,
) : ExploreRepository {

    override fun getPhotos(): Flow<PagingData<Photo>> =
        Pager(
            config = PagingConfig(pageSize = PhotosPagingSource.PAGE_SIZE),
            pagingSourceFactory = { PhotosPagingSource(exploreClient) },
        ).flow
}
