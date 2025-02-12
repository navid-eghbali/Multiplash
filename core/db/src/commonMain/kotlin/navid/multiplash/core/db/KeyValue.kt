package navid.multiplash.core.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class KeyValue(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val key: String,
    val value: String,
)
