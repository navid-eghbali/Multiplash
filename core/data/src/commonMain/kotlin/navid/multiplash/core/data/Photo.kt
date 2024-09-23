package navid.multiplash.core.data

import kotlinx.serialization.Serializable

@Serializable
data class Photo(
    val id: String,
    val urls: Urls,
    val color: String? = null,
)
