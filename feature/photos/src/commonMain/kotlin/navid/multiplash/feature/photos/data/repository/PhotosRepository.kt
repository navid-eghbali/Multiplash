package navid.multiplash.feature.photos.data.repository

import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import app.cash.paging.PagingData
import kotlinx.coroutines.flow.Flow
import navid.multiplash.core.data.Photo
import navid.multiplash.feature.photos.data.paging.PhotosPagingSource
import navid.multiplash.feature.photos.data.remote.PhotosClient

internal interface PhotosRepository {

    fun photos(query: String): Flow<PagingData<Photo>>
}

internal class PhotosRepositoryImpl(
    private val photosClient: PhotosClient,
) : PhotosRepository {

    override fun photos(query: String): Flow<PagingData<Photo>> =
        Pager(
            config = PagingConfig(pageSize = PhotosPagingSource.PAGE_SIZE),
            pagingSourceFactory = { PhotosPagingSource(photosClient, query) },
        ).flow
}
