package navid.multiplash.feature.home.data.repository

import androidx.paging.PagingState
import app.cash.paging.PagingSource
import app.cash.paging.PagingSourceLoadResult
import app.cash.paging.PagingSourceLoadResultError
import app.cash.paging.PagingSourceLoadResultPage
import navid.multiplash.feature.home.data.model.Photo
import navid.multiplash.feature.home.data.remote.HomeClient

internal class PhotosPagingSource(
    private val homeClient: HomeClient,
) : PagingSource<Int, Photo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> = try {
        val nextPageNumber = params.key ?: 1
        homeClient.getPhotos(offset = nextPageNumber).fold(
            onSuccess = {
                PagingSourceLoadResultPage(
                    data = it.photos,
                    prevKey = null,
                    nextKey = nextPageNumber + 1,
                ) as PagingSourceLoadResult<Int, Photo>
            },
            onFailure = {
                PagingSourceLoadResultError<Int, Photo>(it) as PagingSourceLoadResult<Int, Photo>
            },
        )
    } catch (e: Exception) {
        PagingSourceLoadResultError<Int, Photo>(e) as PagingSourceLoadResult<Int, Photo>
    }

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? = null
}
