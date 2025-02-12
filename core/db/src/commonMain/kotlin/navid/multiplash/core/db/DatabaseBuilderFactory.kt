package navid.multiplash.core.db

import androidx.room.RoomDatabase

expect class DatabaseBuilderFactory {
    fun create(fileName: String): RoomDatabase.Builder<KeyValueDatabase>
}
