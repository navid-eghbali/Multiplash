package navid.multiplash.core.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface KeyValueDao {

    @Upsert
    suspend fun upsert(data: KeyValue)

    @Query("SELECT count(*) FROM KeyValue")
    suspend fun count(): Int

    @Query("SELECT * FROM KeyValue WHERE `key` = :key")
    suspend fun get(key: String): KeyValue?

    @Query("SELECT * FROM KeyValue WHERE `key` = :key")
    fun getAsFlow(key: String): Flow<KeyValue?>

    @Query("SELECT * FROM KeyValue")
    suspend fun getAll(): List<KeyValue>

    @Query("SELECT * FROM KeyValue LIMIT :pageSize OFFSET :page")
    suspend fun getAllPaged(page: Int, pageSize: Int): List<KeyValue>

    @Query("SELECT * FROM KeyValue")
    fun getAllAsFlow(): Flow<List<KeyValue>>

    @Query("DELETE FROM KeyValue WHERE `key` = :key")
    suspend fun delete(key: String)

    @Query("DELETE FROM KeyValue")
    suspend fun deleteAll()
}
