package navid.multiplash.feature.user.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import navid.multiplash.core.data.ImageUrl
import navid.multiplash.core.data.Photo
import navid.multiplash.core.data.Tag

internal object GetUser {

    @Serializable
    data class Response(
        val id: String,
        val username: String,
        val name: String,
        val bio: String?,
        val location: String?,
        @SerialName("profile_image") val profileImage: ImageUrl,
        @SerialName("total_collections") val totalCollections: Long,
        @SerialName("total_likes") val totalLikes: Long,
        @SerialName("total_photos") val totalPhotos: Long,
        val photos: List<Photo>,
        val tags: Tags,
        @SerialName("followers_count") val followersCount: Long?,
        @SerialName("following_count") val followingCount: Long?,
        val downloads: Long,
    ) {
        @Serializable
        data class Tags(
            @SerialName("custom") val interests: List<Tag>,
        )
    }

    fun getPath(username: String): String = "/users/$username"
}
