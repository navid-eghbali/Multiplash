package navid.multiplash.feature.details.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import navid.multiplash.core.data.Tag
import navid.multiplash.core.data.User

internal object GetPhoto {

    @Serializable
    data class Response(
        val id: String,
        val color: String? = null,
        val views: Int,
        val downloads: Int,
        val likes: Int,
        @SerialName("created_at") val createdAt: String,
        val description: String?,
        val user: User,
        val exif: Exif?,
        val location: Location,
        val tags: List<Tag>,
        val topics: List<Topic>,
        val links: Links,
    ) {

        @Serializable
        data class Exif(
            val name: String?,
        )

        @Serializable
        data class Location(
            val name: String?,
        )

        @Serializable
        data class Topic(
            val id: String,
            val title: String,
        )

        @Serializable
        data class Links(
            val download: String,
        )
    }

    fun getPath(photoId: String): String = "/photos/$photoId"
}
