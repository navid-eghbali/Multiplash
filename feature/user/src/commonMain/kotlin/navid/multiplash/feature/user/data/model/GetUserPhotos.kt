package navid.multiplash.feature.user.data.model

import io.ktor.http.Parameters
import io.ktor.http.parameters
import navid.multiplash.core.data.Photo

internal object GetUserPhotos {

    data class Response(val photos: List<Photo>)

    fun getPath(username: String): String = "/users/$username/photos"

    fun getQueryParameters(
        page: Int,
        pageSize: Int,
    ): Parameters = parameters {
        append("page", page.toString())
        append("per_page", pageSize.toString())
        append("order_by", "latest")
    }
}
