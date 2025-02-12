package navid.multiplash.feature.details.data.repository

import kotlinx.coroutines.flow.Flow
import navid.multiplash.core.db.KeyValue
import navid.multiplash.core.db.KeyValueDao

internal interface DetailsRepository {
    suspend fun bookmarkPhoto(
        photoId: String,
        photoUrl: String,
    ): Result<Unit>

    suspend fun unbookmarkPhoto(photoId: String): Result<Unit>

    fun getBookmarks(): Flow<List<KeyValue>>
}

internal class DetailsRepositoryImpl(
    private val keyValueDao: KeyValueDao
) : DetailsRepository {
    override suspend fun bookmarkPhoto(photoId: String, photoUrl: String): Result<Unit> = runCatching {
        keyValueDao.upsert(
            data = KeyValue(
                key = photoId,
                value = photoUrl
            )
        )
    }

    override suspend fun unbookmarkPhoto(photoId: String): Result<Unit> = runCatching {
        keyValueDao.delete(key = photoId)
    }

    override fun getBookmarks(): Flow<List<KeyValue>> = keyValueDao.getAllAsFlow()
}
