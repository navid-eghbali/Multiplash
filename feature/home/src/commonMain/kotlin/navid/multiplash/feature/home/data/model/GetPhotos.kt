package navid.multiplash.feature.home.data.model

import kotlinx.serialization.Serializable

object GetPhotos {

    @Serializable
    data class Response(val photos: List<Photo>)
}

@Serializable
data class Photo(
    val id: String,
    val urls: Urls,
    val width: Int,
    val height: Int
)

@Serializable
data class Urls(val regular: String)

