package navid.multiplash.core.api.data

import kotlinx.serialization.Serializable

@Serializable
data class Photo(
    val id: String,
    val urls: Urls,
    val width: Int,
    val height: Int
)
