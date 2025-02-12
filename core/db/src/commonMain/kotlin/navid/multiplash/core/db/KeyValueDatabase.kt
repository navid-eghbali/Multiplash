package navid.multiplash.core.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [KeyValue::class], version = 1)
abstract class KeyValueDatabase : RoomDatabase() {
    abstract fun keyValueDao(): KeyValueDao
}
