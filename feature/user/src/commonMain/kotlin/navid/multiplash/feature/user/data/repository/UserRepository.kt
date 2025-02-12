package navid.multiplash.feature.user.data.repository

import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import app.cash.paging.PagingData
import kotlinx.coroutines.flow.Flow
import navid.multiplash.core.data.Photo
import navid.multiplash.core.db.KeyValue
import navid.multiplash.core.db.KeyValueDao
import navid.multiplash.feature.user.data.paging.UserPhotosPagingSource
import navid.multiplash.feature.user.data.remote.UserClient

internal interface UserRepository {

    fun getUserPhotos(username: String): Flow<PagingData<Photo>>

    suspend fun favoriteUser(
        username: String,
        profileImage: String,
    ): Result<Unit>

    suspend fun unfavoriteUser(username: String): Result<Unit>

    fun getFavorites(): Flow<List<KeyValue>>
}

internal class UserRepositoryImpl(
    private val userClient: UserClient,
    private val keyValueDao: KeyValueDao,
) : UserRepository {

    override fun getUserPhotos(username: String): Flow<PagingData<Photo>> =
        Pager(
            config = PagingConfig(pageSize = UserPhotosPagingSource.PAGE_SIZE),
            pagingSourceFactory = { UserPhotosPagingSource(userClient, username) },
        ).flow

    override suspend fun favoriteUser(username: String, profileImage: String): Result<Unit> = runCatching {
        keyValueDao.upsert(
            data = KeyValue(
                key = username,
                value = profileImage
            )
        )
    }

    override suspend fun unfavoriteUser(username: String): Result<Unit> = runCatching {
        keyValueDao.delete(key = username)
    }

    override fun getFavorites(): Flow<List<KeyValue>> = keyValueDao.getAllAsFlow()
}
