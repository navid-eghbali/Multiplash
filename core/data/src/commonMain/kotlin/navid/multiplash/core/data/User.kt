package navid.multiplash.core.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val username: String,
    val name: String,
    val bio: String? = null,
    @SerialName("profile_image") val profileImage: ImageUrl,
    @SerialName("total_photos") val totalPhotos: Int,
)
