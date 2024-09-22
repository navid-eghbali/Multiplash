package navid.multiplash.core.data

import kotlinx.serialization.Serializable

@Serializable
data class ImageUrl(
    val small: String,
    val medium: String,
    val large: String,
)
