package navid.multiplash.core.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Topic(
    val id: String,
    val title: String,
    val description: String,
    @SerialName("total_photos") val totalPhotos: Int,
    val owners: List<User>,
    @SerialName("top_contributors") val topContributors: List<User>? = null,
    @SerialName("cover_photo") val coverPhoto: Photo,
    @SerialName("preview_photos") val previewPhotos: List<Photo>,
)
