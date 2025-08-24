package navid.multiplash.core.db

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor

@Database(entities = [KeyValue::class], version = 1)
@ConstructedBy(KeyValueDatabaseConstructor::class)
abstract class KeyValueDatabase : RoomDatabase() {
    abstract fun keyValueDao(): KeyValueDao
}

@Suppress("KotlinNoActualForExpect")
expect object KeyValueDatabaseConstructor : RoomDatabaseConstructor<KeyValueDatabase> {
    override fun initialize(): KeyValueDatabase
}
