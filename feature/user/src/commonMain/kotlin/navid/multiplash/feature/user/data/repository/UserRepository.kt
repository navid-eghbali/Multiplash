package navid.multiplash.feature.user.data.repository

import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import app.cash.paging.PagingData
import kotlinx.coroutines.flow.Flow
import navid.multiplash.core.data.Photo
import navid.multiplash.feature.user.data.paging.UserPhotosPagingSource
import navid.multiplash.feature.user.data.remote.UserClient

internal interface UserRepository {

    fun getUserPhotos(username: String): Flow<PagingData<Photo>>
}

internal class UserRepositoryImpl(
    private val userClient: UserClient,
) : UserRepository {

    override fun getUserPhotos(username: String): Flow<PagingData<Photo>> =
        Pager(
            config = PagingConfig(pageSize = UserPhotosPagingSource.PAGE_SIZE),
            pagingSourceFactory = { UserPhotosPagingSource(userClient, username) },
        ).flow
}
