package navid.multiplash.core.db

import androidx.room.Room
import androidx.room.RoomDatabase
import java.io.File

actual class DatabaseBuilderFactory {
    actual fun create(fileName: String): RoomDatabase.Builder<KeyValueDatabase> {
        val dbFile = File(System.getProperty("java.io.tmpdir"), fileName)
        return Room.databaseBuilder(dbFile.absolutePath)
    }
}
