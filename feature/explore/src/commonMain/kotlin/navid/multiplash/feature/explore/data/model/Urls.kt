package navid.multiplash.feature.explore.data.model

import kotlinx.serialization.Serializable

@Serializable
internal data class Urls(
    val raw: String,
    val full: String,
    val regular: String,
    val small: String,
    val thumb: String,
)
