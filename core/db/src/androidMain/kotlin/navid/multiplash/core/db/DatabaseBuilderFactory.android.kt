package navid.multiplash.core.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

actual class DatabaseBuilderFactory(private val context: Context) {
    actual fun create(fileName: String): RoomDatabase.Builder<KeyValueDatabase> {
        val dbFile = context.getDatabasePath(fileName)
        return Room.databaseBuilder(
            context = context,
            name = dbFile.absolutePath,
        )
    }
}
