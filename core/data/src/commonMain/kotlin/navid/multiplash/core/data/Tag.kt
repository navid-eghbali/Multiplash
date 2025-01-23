package navid.multiplash.core.data

import kotlinx.serialization.Serializable

@Serializable
data class Tag(
    val type: String,
    val title: String,
)
