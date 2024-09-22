package navid.multiplash.feature.search.data.model

import io.ktor.http.Parameters
import io.ktor.http.parameters
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

internal object SearchPhotos {

    @Serializable
    data class Response(
        val total: Int,
        @SerialName("total_pages") val totalPages: Int,
        val results: List<Photo>,
    )

    fun getPath(): String = "/search/photos"

    fun getQueryParameters(
        query: String,
        page: Int,
        pageSize: Int,
    ): Parameters = parameters {
        append("query", query)
        append("per_page", pageSize.toString())
        append("page", page.toString())
        append("order_by", "relevant")
    }
}

@Serializable
internal data class Photo(
    val id: String,
    val urls: Urls,
    val width: Int,
    val height: Int,
)
