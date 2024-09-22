package navid.multiplash.feature.explore.data.model

import io.ktor.http.Parameters
import io.ktor.http.parameters
import navid.multiplash.core.data.Photo

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
