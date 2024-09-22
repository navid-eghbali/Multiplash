package navid.multiplash.feature.search.data.model

import io.ktor.http.Parameters
import io.ktor.http.parameters
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

internal object GetTopics {

    data class Response(val topics: List<Topic>)

    fun getPath(): String = "/topics"

    fun getQueryParameters(): Parameters = parameters {
        append("order_by", "featured")
        append("per_page", "30")
        append("page", "1")
    }
}

@Serializable
internal data class Topic(
    val id: String,
    val title: String,
    @SerialName("total_photos") val totalPhotos: Int,
    @SerialName("cover_photo") val coverPhoto: CoverPhoto,
)

@Serializable
internal data class CoverPhoto(
    val color: String,
    val urls: Urls,
)
