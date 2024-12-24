package navid.multiplash.feature.photos.data.model

import io.ktor.http.Parameters
import io.ktor.http.parameters
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import navid.multiplash.core.data.Photo

internal object Photos {

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
