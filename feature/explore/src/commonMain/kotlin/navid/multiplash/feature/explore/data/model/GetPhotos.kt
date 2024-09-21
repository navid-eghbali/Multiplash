package navid.multiplash.feature.explore.data.model

import io.ktor.http.Parameters
import io.ktor.http.parameters
import kotlinx.serialization.Serializable

internal object GetPhotos {

    data class Response(val photos: List<Photo>)

    fun getPath(): String = "/photos"

    fun getQueryParameters(page: Int, pageSize: Int): Parameters = parameters {
        append("order_by", "popular")
        append("orientation", "squarish")
        append("per_page", pageSize.toString())
        append("page", page.toString())
    }
}

@Serializable
internal data class Photo(
    val id: String,
    val urls: Urls,
    val width: Int,
    val height: Int
)

@Serializable
internal data class Urls(val regular: String)

