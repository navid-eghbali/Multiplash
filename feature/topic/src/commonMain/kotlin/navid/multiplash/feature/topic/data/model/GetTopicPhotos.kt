package navid.multiplash.feature.topic.data.model

import io.ktor.http.Parameters
import io.ktor.http.parameters
import navid.multiplash.core.data.Photo

internal object GetTopicPhotos {

    data class Response(val photos: List<Photo>)

    fun getPath(topicId: String): String = "/topics/$topicId/photos"

    fun getQueryParameters(
        page: Int,
        pageSize: Int,
    ): Parameters = parameters {
        append("page", page.toString())
        append("per_page", pageSize.toString())
        append("order_by", "latest")
    }
}
